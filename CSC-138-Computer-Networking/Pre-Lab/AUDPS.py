from socket import *

serverPort = 12345
serverHost = '130.86.67.142'
serverSocket = socket(AF_INET, SOCK_DGRAM)
serverSocket.bind((serverHost, serverPort))

print ('The server is ready to receive')
while True:
	message, clientAddress = serverSocket.recvfrom(2048)
	modifiedMessage = message.decode().upper()
	serverSocket.sendto(modifiedMessage.encode(),clientAddress)
