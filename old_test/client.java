import java.io.*;
import java.net.*;


public class client {

    public client() {
        boolean clientRunning = true;

        try (
                Socket echoSocket = new Socket("localhost", 2555);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader (new InputStreamReader(System.in))
        ){
            String userInput;

            while (clientRunning == true) {
                 while ((userInput = stdIn.readLine()) != null) {
                    System.out.println("sent message");
                    out.println(userInput);
                    System.out.println("echo: " + in.readLine());
                }
            }

            // Close the client
            System.exit(1);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + "localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + "localhost");
            System.exit(1);
    }

}

    public static void main (String[] args) {
        client gameClient = new client(); // Create an instance of the client
    }
}
