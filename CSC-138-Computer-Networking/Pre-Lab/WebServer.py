#import socket module 
from socket import * 
import sys # In order to terminate the program 
 
serverSocket = socket(AF_INET, SOCK_STREAM) #Prepare a sever socket 
serverSocket.bind(('130.86.67.142', 12345))
serverSocket.listen(1) 
while True:     
	#Establish the connection     
  	print('Ready to serve...')     
  	connectionSocket, addr = serverSocket.accept()
	try:
		message = connectionSocket.recv(4096)
        	filename = message.split()[1]
        	f = open(filename[1:])
        	outputdata = f.readlines()
        	#Send one HTTP header line into socket
		HEADER = "HTTP/1.1 200 OK\nContent-Type: "
		FileType = "text/html\n\n"
		HEADER += str(FileType)
		connectionSocket.send(HEADER)
		#Send the content of the requested file to the client
	        for i in range(0, len(outputdata)):
        		connectionSocket.send(outputdata[i].encode())
        	connectionSocket.send("\r\n".encode())
        	connectionSocket.close()
    	except IOError:
    		#Send response message for file not found
    		connectionSocket.send("HTTP/ 1.1 404 Not Found\nContent-Type: text/html\n\n")
		HTML = "<html><head></head><body><h1>404 Not Found</h1></body></html>\r\n"
        	connectionSocket.send(HTML)
		#Close client socket
    		connectionSocket.close()
serverSocket.close()
sys.exit()
