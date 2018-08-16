package webserver;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) {
        // TODO code application logic here

        try {

            ServerSocket serverSocket = new ServerSocket(8080); // Create a server socket object
            boolean isStop = false;

            while(!isStop) { // While server is not stopped
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client " + clientSocket.getInetAddress().getHostAddress() + " is connected");
                ClientThread clientThread = new ClientThread(clientSocket);
                clientThread.start();
            }
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

}

