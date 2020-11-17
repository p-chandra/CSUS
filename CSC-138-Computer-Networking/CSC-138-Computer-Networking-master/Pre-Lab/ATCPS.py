from socket import *

hostName = '130.86.67.142'
serverPort = 12222

serverSocket = socket(AF_INET,SOCK_STREAM)
serverSocket.bind((hostName,serverPort))
serverSocket.listen(1)
print "The server is ready to receive"
while True:
	connectionSocket, addr = serverSocket.accept()
	sentence = connectionSocket.recv(1024).decode()
	capitalizedSentence = sentence.upper()
	connectionSocket.send(capitalizedSentence.encode())
	connectionSocket.close()
