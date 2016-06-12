import org.lwjgl.system.CallbackI;

import java.io.IOException;
import java.net.ServerSocket;
import java.lang.*;
import java.io.*;
import java.net.*;


/**
 * Created by Jack on 6/11/2016.
 */
public class server {

    public server() {

        boolean serverEnabled = true;

        try {
            ServerSocket srvr = new ServerSocket(2555);
            Socket clientSocket = srvr.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine, outputLine;
            // Server loop
            while (serverEnabled == true) {
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("test") == true) {
                        System.out.println("I got the message " + inputLine);
                        out.println("echo back to client");
                    }
                    else if (inputLine.equals("close") == true) {
                        out.print("I got the test message");
                        System.out.println("closing the server");
                        serverEnabled = false;
                    }
                    else {
                        System.out.println("Error undefined message '" + inputLine + "'" + " sent from the client");
                        out.println("Error Undefined message");
                    }
                }
            }

            // Close the socket
            clientSocket.close();
            // Close the server
            srvr.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong with starting the server");
        }
    }

    public static void main (String[] args) {
        System.out.print("Attempting to start the server");

        // Create an instance of the server
        server gameServer = new server();
    }

}
