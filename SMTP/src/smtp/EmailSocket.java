package smtp;

import java.net.*;
import java.io.*;

public class EmailSocket {
    private static Socket smtpSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static void main(String[] args) {
        try {
            smtpSocket = new Socket("localhost", 25);
            out = new PrintWriter(smtpSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(smtpSocket.getInputStream()));
        } catch (UnknownHostException e1) {
            System.out.println("Host unknown");
        } catch (IOException e2) {
            System.out.println("Couldn't get IO for the connection");
        }

        if (smtpSocket != null && out != null && in != null) {
            try {
                /*
                STEP 1 Get a greeting by the server
                */
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("220") != -1) {
                        break;
                    }
                }

                /*
                STEP 2 The client initiates its dialog by responding with a HELO command identifying itself
                */

                out.println("HELO " + InetAddress.getLocalHost().getHostAddress());
                System.out.println("HELO " + InetAddress.getLocalHost().getHostAddress());
                while ((responseLine = in.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("250") != -1) {
                        break;
                    }
                }

                /*
                STEP 3 The client notifies the receiver of the originating email address of the message in a MAIL FROM command.
                */

                out.println("MAIL From: mytest@test.com");
                while ((responseLine = in.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("250") != -1) {
                        break;
                    }
                }

                /*
                STEP 4 The client notifies the receiver of the recipient email address of the message in a RCPT TO command.
                */

                out.println("RCPT TO: horatiolsh@gmail.com");
                while ((responseLine = in.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("250") != -1) {
                        break;
                    }
                }

                /*
                STEP 5  Send DATA command
                */

                out.println("DATA");
                while ((responseLine = in.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("354") != -1) {
                        break;
                    }
                }

                /*
                STEP 6  Send Email Body
                */

                out.println("From: mytest@test.com");
                out.println("To: jnetworkprogramming@gmail.com");
                out.println("Subject: TEST EMAIL");
                out.println();
                out.println("Subject: TEST EMAIL"); // message body
                out.println("This is a test message"); // message body
                out.println("Thanks,"); // message body
                out.println("Nihao"); // message body
                out.println();
                out.println(".");

                while ((responseLine = in.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("250") != -1) {
                        break;
                    }
                }

                /*
                STEP 7  Send Quit command
                */

                out.println("QUIT");

                while ((responseLine = in.readLine()) != null) {
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("221") != -1) {
                        break;
                    }
                }

                System.out.println("Email successfully sent!");

                //close the output stream
                // close the input stream
                // close the socket
                out.close();
                in.close();
                smtpSocket.close();

            }
            catch(Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
