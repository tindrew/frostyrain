package Rex;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


/**
 * The Window class is a singleton responsible for managing a window
 * that utilizes the LWJGL library for OpenGL rendering.
 * It provides methods for initialization and the main event loop to manage
 * the application's graphical interface.
 * The Window is designed to have only one instance during the application's lifetime.
 */
public class Window {
   private int width, height;
   private String title;
   private Long glfwWindow;

    // a singleton because we will only ever have one instance of the window
    private static Window window = null;

    // Private is to ensure nothing else can make a window
    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException(("Failed to create the GLFW window"));
        }

        // Registers callback functions to handle different mouse events for the specified GLFW window.
// - `glfwSetCursorPosCallback`: Sets the callback for mouse movement, linking it to the `mousePosCallback` method in MouseListener.
// - `glfwSetMouseButtonCallback`: Sets the callback for mouse button actions (press/release), linking it to the `mouseButtonCallback` method in MouseListener.
// - `glfwSetScrollCallback`: Sets the callback for mouse scroll events, linking it to the `mouseScrollCallback` method in MouseListener.
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window Visible.
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
        // This line is critical for LWJGL's interoperation with FLGW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the openGL
        // bindings available for use.
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
                System.out.println("Space key is pressed");
            }


            glfwSwapBuffers(glfwWindow);
        }
    }


}
