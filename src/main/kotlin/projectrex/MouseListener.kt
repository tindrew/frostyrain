package projectrex

import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE

// Class MouseListener. In Kotlin, constructors are combined with class setup,
// which is why there's a weird "private constructor()" after class declaration.
class MouseListener {
    private var scrollX: Double? = 0.0
    private var scrollY: Double? = 0.0
    private var xPosition: Double? = 0.0
    private var yPosition: Double? = 0.0
    private var lastX: Double? = 0.0
    private var lastY: Double? = 0.0
    private var mouseButtonPressed = BooleanArray(3)
    private var isDragging: Boolean? = false

    companion object {
        private var instance: MouseListener? = null

    }

    fun get(): MouseListener {
        if (instance == null) {
            instance = MouseListener()

        }
        // Return the instance, using a !! to assert it's non-null after the check
        return instance!!
    }

    public fun mousePosCallback(window: Long, xpos: Double, ypos: Double) {
        get().lastX = get().xPosition
        get().lastY = get().yPosition
        get().xPosition = xpos
        get().yPosition = ypos
        get().isDragging = get().mouseButtonPressed[0] or get().mouseButtonPressed[1] or get().mouseButtonPressed[2]
    }

    public fun mouseButtonCallback(window: Long, button: Int, action: Int, mods: Int) {
        if (action == GLFW_PRESS) {
            if (button < get().mouseButtonPressed.size)
            get().mouseButtonPressed[button] = true
        } else if (action == GLFW_RELEASE) {
            get().mouseButtonPressed[button] = false
            get().isDragging = false
        }
    }

    public fun mouseScrollCallback(window: Long, xOffset: Double, yOffset: Double) {
        get().scrollX = xOffset
        get().scrollY = yOffset
    }

    public fun endFrame() {
        get().scrollX = 0.0
        get().scrollY = 0.0
        get().lastX = get().xPosition
        get().lastY = get().yPosition
    }

    fun getX(): Float {
        return get().lastX!!.toFloat()
    }

    fun getY(): Float {
        return get().lastY!!.toFloat()
    }

    fun getDx(): Float {
        return get().lastX!!.toFloat() - get().xPosition!!.toFloat()
    }

    fun getDy(): Float {
        return get().lastY!!.toFloat() - get().yPosition!!.toFloat()
    }

    fun getScrollX(): Float {
        return get().scrollX!!.toFloat()
    }

    fun getScrollY(): Float {
        return get().scrollY!!.toFloat()
    }

    fun isDragging(): Boolean {
        return get().isDragging!!
    }

    fun mouseButtonDown(button: Int): Boolean {
        if (button < get().mouseButtonPressed.size) {
            return get().mouseButtonPressed[button]
        } else {
            return false
        }
    }
}












