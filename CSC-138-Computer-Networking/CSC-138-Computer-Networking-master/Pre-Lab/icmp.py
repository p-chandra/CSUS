from socket import *
import os
import sys
import struct
import time
import select
import binascii
import socket


ICMP_ECHO_REQUEST = 8
timeRTT = []
packageSent =0;
packageRev = 0;

#As given in Lab Example


def checksum(str):
    csum = 0
    countTo = (len(str) / 2) * 2
    count = 0
    while count < countTo:
        thisVal = ord(str[count+1]) * 256 + ord(str[count])
        csum = csum + thisVal
        csum = csum & 0xffffffffL
        count = count + 2
    if countTo < len(str):
        csum = csum + ord(str[len(str) - 1])
        csum = csum & 0xffffffffL
    csum = (csum >> 16) + (csum & 0xffff)
    csum = csum + (csum >> 16)
    answer = ~csum
    answer = answer & 0xffff
    answer = answer >> 8 | (answer << 8 & 0xff00)
    return answer



def receiveOnePing(mySocket, ID, timeout, destAddr):
    global packageRev,timeRTT
    timeLeft = timeout
    while 1:
        startedSelect = time.time()
        whatReady = select.select([mySocket], [], [], timeLeft)
        howLongInSelect = (time.time() - startedSelect)
        if whatReady[0] == []: # Timeout
            return "0: Destination Network Unreachable,"
        timeReceived = time.time()
        recPacket, addr = mySocket.recvfrom(1024)

       
        icmpHeader = recPacket[20:28]
        requestType, code, revChecksum, revId, revSequence = struct.unpack('bbHHh',icmpHeader)
        if ID == revId:
            bytesInDouble = struct.calcsize('d')
            timeData = struct.unpack('d',recPacket[28:28 + bytesInDouble])[0]
            timeRTT.append(timeReceived - timeData)
            packageRev += 1
            return timeReceived - timeData #From Lab assignment reference
        else:
            return "Request timed out "
        
       
        timeLeft = timeLeft - howLongInSelect
        if timeLeft <= 0:
            return "1: Host Unreachable."

        
def sendOnePing(mySocket, destAddr, ID):
    global packageSent
    # Header is type (8), code (8), checksum (16), id (16), sequence (16)
    myChecksum = 0

    # A dummy header with 0 as a checksum
   
    header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, ID, 1)
    data = struct.pack("d", time.time())
    
   
    
    myChecksum = checksum(header + data)
    
   
    if sys.platform == 'darwin':
        myChecksum = socket.htons(myChecksum) & 0xffff
        
    else:
        myChecksum = socket.htons(myChecksum)
    header = struct.pack("bbHHh", ICMP_ECHO_REQUEST, 0, myChecksum, ID, 1)
    packet = header + data
    mySocket.sendto(packet, (destAddr, 1))
    packageSent += 1
     # AF_INET address must be tuple, not str
    #Both LISTS and TUPLES consist of a number of objects
    #which can be referenced by their position number within the object
def doOnePing(destAddr, timeout):
    icmp = socket.getprotobyname("icmp")
    
#Create Socket here/ Fill in Star and End
    try:
        mySocket = socket.socket(socket.AF_INET, socket.SOCK_RAW, icmp)
    except socket.error, (errno, msg):
        if errno == 1:
            raise socket.error(msg)
    
    myID = os.getpid() & 0xFFFF
    sendOnePing(mySocket, destAddr, myID)
    delay = receiveOnePing(mySocket, myID, timeout, destAddr)
    
    mySocket.close()
    return delay

def ping(host, timeout=1):
    
    dest = socket.gethostbyname(host)
    print "Pinging " + dest + " using Python:"
    print ""
    #Send ping requests to a server separated by approximately one second
    while 1 :
        delay = doOnePing(dest, timeout)
        print "RTT:", test delay
        print "maxRTT:", (max(timeRTT) if len(timeRTT) > 0 else 0), "\tminRTT:", (min(timeRTT) if len(timeRTT) > 0 else 0), "\naverageRTT:", float(sum(timeRTT)/len(timeRTT) if len(timeRTT) > 0 else float("nan"))
        print "Package Loss:", ((packageSent - packageRev)/packageSent if packageRev > 0 else 0)
        time.sleep(1)
    return delay

ping("www.google.com")