package webserver;

import java.io.*;
import java.net.*;
import java.util.Date;

public class ClientThread extends Thread {
    private Socket socket;
    private boolean isStop;
    private BufferedReader in;
    private PrintWriter out;
    private File file;
    final static String CRLF = "\r\n";

    public ClientThread(Socket clientSocket) {
        this.socket = clientSocket;
        this.isStop = false;
    }

    public void run() {
        try {
            while(!isStop) {
                //create a buffer reader
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //create a PrintWriter
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                String line;
                String httpHeader = ""; // Stores the html header
                String htmlFile =""; // Stores the required html file
                while (true) {
                    line = in.readLine(); // Read each line
                    if (line.equals(CRLF) || line.equals("")) { // End of header is reached?
                        break; // If yes, break
                    }
                    httpHeader = httpHeader + line + "\n"; // Add a new line to the header
                    if(line.contains("GET")) { // If line contains get
                        // Extract the html filename
                        int beginIndex = line.indexOf("/");
                        int endIndex = line.indexOf(" HTTP");
                        htmlFile = line.substring(beginIndex+1, endIndex);
                    }
                }
                // System.out.println(httpHeader);

                // System.out.println("file: " + htmlFile);

                processRequest(htmlFile);
                closeConnection();

            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void processRequest(String htmlFile) throws Exception {
        File file = new File(htmlFile); // Create a file variable
        if(file.exists()) { // If file exists
            // Create a BufferedReader to read the html file content
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // Sent the HTTP head (HTTP 200 OK)
            out.print("HTTP/1.0 200 OK" + CRLF);
            Date date = new Date();
            out.print("Date: " + date.toString() + CRLF);
            out.print("Server: java tiny web server" + CRLF);
            out.print("Content-Type: text/html" + CRLF);
            out.print("Content-Length: " + file.length() + CRLF);
            out.println("Content-Type: text/html; charset=iso-8859-1" + CRLF);
            // End of http header

            String line = "";
            while((line = reader.readLine()) != null) { // Read a line from the html file
                out.println(line); // Write the line to the socket connection
            }
        } else { // If file does not exists
            // Sent the HTTP head (404 Not Found)
            out.print("HTTP/1.1 404 Not Found" + CRLF);
            Date date = new Date();
            out.print("Date: " + date.toString() + CRLF);
            out.print("Server: java tiny web server" + CRLF);
            out.print("Connection: close" + CRLF);
            out.println("Content-Type: text/html; charset=iso-8859-1" + CRLF);
            // End of http header

            // Send file not found message
            out.println("<html><head>");
            out.println("<title>404 Not Found</title>");
            out.println("</head><body>");
            out.println("<h1>Not Found</h1>");
            out.println("<p>The requested URL /" + htmlFile + " was not found on this server.</p>");
            out.println("</body></html>");
            out.println(CRLF);
        }
    }

    private void closeConnection() {
        try {
            out.close(); // Close output stream
            in.close(); // Close input stream
            socket.close(); // Close socket
            isStop = true; // Set isStop to true in order to exist the while loop
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
