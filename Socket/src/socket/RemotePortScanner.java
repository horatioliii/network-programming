package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class RemotePortScanner {
    public static void main(String[] args) {
        // Create a buffered reader to read the user input
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(in);

        String targetIp = ""; // Store the target ip address
        int fromPort = 0; // Store the starting port
        int toPort = 0; // Store the ending port

        System.out.println("Please enter the target ip address: "); // Ask user input

        try {
            targetIp = reader.readLine();
        } catch (Exception e) {
            System.out.println("Can't read ip address! " + e.toString());
        }

        // Validation
        boolean isValid = false;

        while (!isValid) {
            try {
                System.out.print("Please enter the first port: ");
                String portString = reader.readLine();
                fromPort = Integer.parseInt(portString);
                if (fromPort >= 0 && fromPort <= 65536) {
                    isValid = true;
                } else {
                    System.out.println("Invalid port! Port range is: 0 ~ 635536");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please insert a number!");
            } catch (Exception e) {
                System.out.println("Cannot read the to port number! " + e.toString());
            }
        }

        // Reinitialize validation
        isValid = false;

        while (!isValid) {
            try {
                System.out.print("Please enter the last port: ");
                String portString = reader.readLine();
                toPort = Integer.parseInt(portString);
                if (toPort >= 0 && toPort <= 65536) {
                    if (toPort > fromPort) {
                        isValid = true;
                    }
                } else {
                    System.out.println("Invalid port! Port range is: 0 ~ 635536");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please insert a number!");
            } catch (Exception e) {
                System.out.println("Cannot read the last port number! " + e.toString());
            }
        }

        int port = fromPort;
        while (port >= fromPort && port <= toPort) {
            try {
                Socket socket = new Socket(targetIp, port);
                System.out.println("Port " + port + " is in listening state!");
                socket.close();
            } catch (UnknownHostException e1) {
                System.out.println("Unkown host exception " + e1.toString());
            } catch (IOException e2) {
                System.out.println("Port " + port + " is not open.");
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            port++;
        }
    }
}
