package projectrex


import org.lwjgl.Version
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryUtil.NULL

class Window {
    private var width: Int? = null
    private var height: Int? = null
    private var title: String? = null
    private var glfwWindow: Long = 0L

    private fun window() {
        this.width = 1920
        this.height = 1080
        this.title = "Project Platformer"
    }

    companion object {
        private var window: Window? = null
        fun get(): Window {
            if (window == null) {
                window = Window()
            }

            return window!!
        }
    }

    fun run() {
        println("Hello LWJGL" + Version.getVersion() + "!")
        window()
        init()
        loop()

        // Free the memory
        glfwFreeCallbacks(glfwWindow)
        glfwDestroyWindow(glfwWindow)

        // Terminate GLFW and the free error callback
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    private fun init() {
        // setup an error callback
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

// Configure GLFW
        glfwDefaultWindowHints() // Optional, the default window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // The window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE)

        // Create the window
        glfwWindow = glfwCreateWindow(this.width!!, this.height!!, this.title!!, NULL, NULL)
        if (glfwWindow == NULL) {
            throw IllegalStateException("Failed to initialize GLFW Window")
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener()::mousePosCallback)
        glfwSetMouseButtonCallback(glfwWindow, MouseListener()::mouseButtonCallback)
        glfwSetScrollCallback(glfwWindow, MouseListener()::mouseScrollCallback)
        glfwSetKeyCallback(glfwWindow, KeyListener()::keyCallback)

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow)
        // Enable v-sync
        glfwSwapInterval(1)

        // Make the window visible
        glfwShowWindow(glfwWindow)

        // This line is critical for LWJGL's interoperability with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()
    }

    fun loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents()

            glClearColor(1.0f, 0.0f,0.0f,0.0f)
            glClear(GL_COLOR_BUFFER_BIT)

           if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
               println("Space key is pressed ")
           }
            glfwSwapBuffers(glfwWindow)
        }
    }
}


































