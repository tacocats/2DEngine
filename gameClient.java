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
    PrintWriter out;
    public BufferedReader in;
    public BufferedReader stdIn;

    // Create a connection to the server when a new instance is created
    public serverConnection() {
        try {
                echoSocket = new Socket("localhost", 2555);
                out = new PrintWriter(echoSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                stdIn = new BufferedReader (new InputStreamReader(System.in));

            System.out.println(out);
            System.out.println("Client Connected to the server!");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + "localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + "localhost");
            System.exit(1);
        }
    }

    public void sendMessage () {
        out.println("test");
        try {
            System.out.println(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
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
    public int posX = 100;
    public int posY = 100;
    public int velX = 0;
    public int velY = 0;


    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glColor3f(05.f, 0.5f, 0.5f);

        glBegin(GL_QUADS);
        glVertex2f(posX, posY);
        glVertex2f(posX+200,posY);
        glVertex2f(posX+200,posY+200);
        glVertex2f(posX,posY+200);
        glEnd();

        glfwSwapBuffers(client);
    }

    public void update (serverConnection server) {
        glfwPollEvents();
        posX += velX;
        posY += velY;

        // Key press input, this needs to be cleaned up and moved to it's own class later on, with public variables to be accessed through custom scripts
        glfwSetKeyCallback(client, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                System.out.println("action " + action);
                // Checks if key was pressed down than what key
                if (action == 1) {
                    switch (key) {
                        case 263: // left
                            velX -= 1;
                            server.sendMessage();
                            break;
                        case 262: // right
                            velX += 1;
                            break;
                        case 265: // down
                            velY -= 1;
                            break;
                        case 264: // up
                            velY += 1;
                            break;
                    }
                } else if (action == 0) {
                    if (key == 263 || key == 262) velX = 0;

                    else if (key == 265 || key == 264) velY = 0;
                }
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

        // Sets the context of GLFW
        glfwMakeContextCurrent(client);

        GL.createCapabilities();

        // Show the window
        glfwShowWindow(client);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));

        // Possibly add this code to loop
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity(); // Resets any previous projection matrices
        glOrtho(0, 640, 480, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
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
