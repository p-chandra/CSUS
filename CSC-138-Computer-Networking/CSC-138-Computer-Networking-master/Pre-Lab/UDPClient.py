from socket import *
import sys
import socket
import time

serverSocket = socket.socket(AF_INET, SOCK_DGRAM)
serverAddress = ('130.86.67.179', 12000)
serverSocket.settimeout(1)
print "Pinging localhost: 12000"
for i in range(1, 11):
	now = time.time()
	minute = now/60
	seconds = now % 60
        message = 'Replied: PING ' + str(i) + " " + time.ctime(now)
        try:
            serverSend = serverSocket.sendto(message, serverAddress)
            data, server = serverSocket.recvfrom(4096)
            end = time.time();
	    elapsed = (end - now) % 60
            print("\tReplied: PING " + str(i) + " RTT = " + str(elapsed) + " s")
        except socket.timeout:
            print("\tRequested Time out")
serverSocket.close()
