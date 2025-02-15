package Rex;

import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    // Private constructor for the MouseListener class to enforce the singleton pattern.
// Initializes the mouse position, scroll values, and other related fields to default values (0.0).
    private MouseListener() {
        this.scrollX = 0.0;  // Horizontal scroll offset
        this.scrollY = 0.0;  // Vertical scroll offset
        this.xPos = 0.0;     // Current x-coordinate of the mouse
        this.yPos = 0.0;     // Current y-coordinate of the mouse
        this.lastX = 0.0;    // Last recorded x-coordinate of the mouse
        this.lastY = 0.0;    // Last recorded y-coordinate of the mouse
    }

    // Static method to retrieve the single instance of MouseListener (Singleton Pattern).
// If no instance exists, it creates one. Otherwise, it returns the existing instance.
// This ensures that only one instance of MouseListener is created during the application's lifetime.
    public static MouseListener get() {
        if (MouseListener.instance == null) { // Check if the instance is null
            MouseListener.instance = new MouseListener(); // Create a new instance if none exists
        }

        return MouseListener.instance; // Return the single instance
    }

    public static void mousePosCallback(long window, double xpos, double ypos) {
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }


    // Static callback method for handling mouse button events.
// This method is triggered when a mouse button is pressed or released on the given window.
// - `window`: The window where the mouse event occurred.
// - `button`: The mouse button involved in the action.
// - `action`: The type of action (e.g., GLFW_PRESS for press, GLFW_RELEASE for release).
// - `mods`: Modifier keys (not used in this implementation).
//
// When a button is pressed, it updates the corresponding index of the `mouseaButtonPressed` array to `true`.
// When released, it sets the value to `false` and stops any dragging action.
    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) { // Check if the button was pressed
            if (button < get().mouseButtonPressed.length) { // Ensure button index is within bounds
                get().mouseButtonPressed[button] = true; // Mark the button as pressed
            }
        } else if (action == GLFW_RELEASE) { // Check if the button was released
            if (button < get().mouseButtonPressed.length) { // Ensure button index is within bounds
                get().mouseButtonPressed[button] = false; // Mark the button as released
                get().isDragging = false; // Reset dragging state
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    // this should be renamed to getMouseX, which is more descriptive
    public static float getX() {
        return (float) get().xPos;
    }

    // this should be renamed to getMouseY, which is more descriptive
    public static float getY() {
        return (float) get().yPos;
    }

    // this should be renamed to getMouseDx
    public static float getDx() {
        return (float) (get().lastX - get().xPos);
    }

    // this should be renamed to getMouseDy
    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    // should be renamed to getMouseScrollX
    public static float getScrollX() {
        return (float) get().scrollX;
    }

    // possibly should be renamed to getMouseScrollY
    public static float getScrollY() {
        return (float) get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        } else {
            return false;
        }
    }
}
