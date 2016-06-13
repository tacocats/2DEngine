// import org.lwjgl.system.CallbackI;


import java.io.IOException;
import java.net.ServerSocket;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// Used for sending information to all running clients
class broadCast extends Thread{

    // List of threads running/clients
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();

    public void messageClients(String message) {

        // Not needed anymore, threads are kept track of in an array
        //Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        //Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);


        // Loops through the list of sockets running and sends the message to them
        for (Socket i : socketList) {
            try {

                PrintWriter out = new PrintWriter (i.getOutputStream(), true);
                out.println(message);
            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }
        }
    }
}

// A client will be assigned a thread on the server
class clientThread extends broadCast {
    public Socket socket;
    public InputStream input = null;
    public BufferedReader reader = null;
    //DataOutputStream out = null;
    public PrintWriter out = null;

    public clientThread (Socket clientSocket) {
        this.socket = clientSocket;
        System.out.println(this.socket);
    }

    public void run() {
        try {
            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            //out = new DataOutputStream(socket.getOutputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            return;
        }

        String inputLine, outputLine;

        while (true) {
            try {
                while ((inputLine = reader.readLine()) != null) {
                    if (inputLine.equals("test") == true) {
                        System.out.println("I got the message " + inputLine);
                        out.println("echo back to client");

                    } else if (inputLine.equals("close") == true) {
                        out.print("I got the test message");
                        System.out.println("closing the server");
                    } else {
                        System.out.println("Error undefined message '" + inputLine + "'" + " sent from the client");
                        out.println("Error Undefined message");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

public class server {

    static final int port = 2555;

    public server() {
        boolean serverEnabled = true;
        ServerSocket srvr = null;
        Socket clientSocket = null;

        try {
            srvr = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();

        }

		while (serverEnabled == true) {
            try {
                clientSocket = srvr.accept();

            } catch (IOException e) {

                System.out.println("I/O error" + e);
            }

            // Create a new thread for the client
            new clientThread(clientSocket).start();
            broadCast.socketList.add(clientSocket);
        }
    }

    public static void main (String[] args) {
        System.out.println("Attempting to start the server");

        // Create an instance of the server
        server gameServer = new server();
    }
}
