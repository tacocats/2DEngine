/**
 * Created by Jack on 6/12/2016.
 */

// Import required GLFW libraries
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.opengl.GL;

import javax.management.MBeanServerConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


// Used for input/output from client to server
class serverConnection {

    public Socket echoSocket;
    public PrintWriter out;
    public BufferedReader in;
    public BufferedReader stdin;

    // Create a connection to the server when a new instance is created
    public serverConnection() {
        try (
                Socket echoSocket = new Socket("localhost", 2555);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader (new InputStreamReader(System.in))
        ){

            System.out.println("Client Connected to the server!");

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + "localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + "localhost");
            System.exit(1);
        }
    }
}

// Creates an instance of the game client
public class gameClient  {

    // We need to strongly reference callback instances.
    // private GLFWErrorCallback errorCallback
    private GLFWKeyCallback keyCallback;

    // Whether or not the game client is running
    public boolean clientRunning = false;
    public long client;



    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(client);
    }

    public void update (serverConnection server) {
        glfwPollEvents();

        // Key press input
        glfwSetKeyCallback(client, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                System.out.println(key);
            }
        });
    }

    public void init () {
        clientRunning = true;
        if ((glfwInit() ? 1 : 0) != GL_TRUE) {
            System.err.println("GLFW Window Handler Library failed to initialize");
        }
        // Initialize GLFW
        glfwInit();

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        client = glfwCreateWindow(640, 480, "Hello World", NULL, NULL);

        if (client == NULL) {
            System.err.println("Could not create our client");
        }

        // Sets the initial position of our game client.
        glfwSetWindowPos(client, 100, 100);
        // Sets the context of GLFW, this is vital for our program to work.
        glfwMakeContextCurrent(client);

        GL.createCapabilities();

        // finally shows our created client in all it's glory.
        glfwShowWindow(client);

        glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));
    }

    public void run() {

        // Initialize the client
        init();

        // Connect to the server
        serverConnection server = new serverConnection();

        while (clientRunning) {
            update(server);
            render();

            if (glfwWindowShouldClose(client)) {
                clientRunning = false;
            }
        }
    }

    public static void main(String[] args) {
        // Create and start a new instance of the game client
        gameClient client = new gameClient();
        client.run();
    }
}
