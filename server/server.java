// import org.lwjgl.system.CallbackI;


import java.io.IOException;
import java.net.ServerSocket;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


// This is a class containing player information. It is used by clientThread to store various information such as player location.
class player {

    public int posX = 0;
    public int posY = 0;
    private Socket clientSocket;

    public player(Socket sentSocket) {
        this.clientSocket = sentSocket;
    }

    // Returns the socket
    public Socket getSocket () {
        return (this.clientSocket);
    }
}


// Class the keeps track of all clients connected to the server
class clientConnections extends Thread {

    // List of threads running/clients

    // Old list only kept track of sockets connected new one keeps track of information as well
    //public static ArrayList<Socket> socketList = new ArrayList<Socket>();
    public static ArrayList<player> clientList = new ArrayList<player>();

}

// Used for sending information to all running clients
class broadCast extends clientConnections {

    public void messageClients(String message) {

        // Not needed anymore, threads are kept track of in an array
        //Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        //Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);

        // Loops through the list of sockets running and sends the message to them
        for (player x : clientList ) {

            Socket i = x.getSocket();
            System.out.println(i);
            try {
                PrintWriter out = new PrintWriter(i.getOutputStream(), true);
                out.println(message);
            } catch (IOException e) {
                System.out.println(e.getStackTrace());
            }
        }
    }
}


// This should be renamed some time in future since it's sorta a stupid name..
// Class that allows calls to get information from other running clients on the server. This reads from connection's client list and get information from the player class of the client
// When is this class of use? When a player connects to the server or something
class severClients extends clientConnections {

    // TODO
    // Gets information from clients already connected to the server and returns it
    public static void getConnectedClients() {

    }
}


// A client will be assigned a thread on the server. This thread retrieves all input from the client and sends information back to the client.
class clientThread extends broadCast {
    public Socket socket;
    public InputStream input = null;
    public BufferedReader reader = null;
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


        //TODO switch all this over to switch statement
        while (true) {
            try {
                while ((inputLine = reader.readLine()) != null) {
                    switch (inputLine) {
                        case "move":
                            messageClients(socket + "moved!");
                            System.out.println("I got the move");
                            break;

                        default:
                            out.println("Error Undefined message");
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

// Start the server
public class server {

    private static final int port = 2555;

    public server() {
        boolean serverEnabled = true;
        ServerSocket srvr = null;
        Socket clientSocket = null;

        try {
            srvr = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();

        }

		while (serverEnabled) {
            try {
                clientSocket = srvr.accept();

            } catch (IOException e) {

                System.out.println("I/O error" + e);
            }

            // Create a new thread for the client
            new clientThread(clientSocket).start();
            clientConnections.clientList.add(new player(clientSocket));
            System.out.println(clientConnections.clientList);
            System.out.println(clientSocket);
            for (player i : clientConnections.clientList) {
                System.out.println("a - " + i.getSocket());
            }
        }
    }

    public static void main (String[] args) {
        System.out.println("Attempting to start the server");

        // Create an instance of the server
        server gameServer = new server();
    }
}
