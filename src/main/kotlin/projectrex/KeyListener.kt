package projectrex

import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE

class KeyListener {
    private var keyPressed = BooleanArray(350)
    companion object {
        private var instance: KeyListener? = null

        public fun isKeyPressed (keyCode: Int): Boolean {
            return get().keyPressed[keyCode]
        }

        public fun get(): KeyListener {
            if (instance == null) {
                instance = KeyListener()
            }

            return KeyListener.instance!!
        }
    }


    public fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false
        }
    }


}
