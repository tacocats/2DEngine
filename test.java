/**
 * Created by Jack on 6/11/2016.
 */


import org.lwjgl.opengl.GL;
import org.lwjgl.system.CallbackI;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;



public class test {

    public long window;
    public boolean running = false;


    public static void main (String[] args) {
        test game = new test();
        game.run();
    }

    public void init() {
        this.running = true;

        if ((glfwInit() ? 1 : 0) != GL_TRUE) {
            System.err.println("GLFW Window Handler Library failed to initialize");
        }
        // Initialize GLFW
        glfwInit();

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        window = glfwCreateWindow(640, 480, "Hello World", NULL, NULL);

        if (window == NULL) {
            System.err.println("Could not create our window");
        }

        // Sets the initial position of our game window.
        glfwSetWindowPos(window, 100, 100);
        // Sets the context of GLFW, this is vital for our program to work.
        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        // finally shows our created window in all it's glory.
        glfwShowWindow(window);

        glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        System.out.println("OpenGL: " + glGetString(GL_VERSION));
    }

    public void run() {
        init();

        while (running == true) {
            update();
            render();

            if (glfwWindowShouldClose(window)) {
                running = false;
            }
        }
    }

    public void update() {
        glfwPollEvents();
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glfwSwapBuffers(window);
    }

}
