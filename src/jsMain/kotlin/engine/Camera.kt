package engine

import engine.core.Core
import engine.js.glMatrix
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.get
import org.khronos.webgl.set

class Camera(
    val wcCenter: Float32Array,
    val wcWidth: Int,
    val viewportArray: Array<Int>,
) {
    val nearPlane = 0
    val farPlane = 1000

    val viewMatrix = glMatrix.mat4.create()
    val projMatrix = glMatrix.mat4.create()
    val vpMatrix = glMatrix.mat4.create()

    var bgColor = Float32Array(arrayOf(.8f, .8f, .8f, 1f))

    fun setWcCenter(xPos: Float, yPos: Float) {
        wcCenter[0] = xPos
        wcCenter[1] = yPos
    }

    fun setupViewProjection(): Float32Array {
        Core.gl.viewport(viewportArray[0], viewportArray[1], viewportArray[2], viewportArray[3])
        Core.gl.scissor(viewportArray[0], viewportArray[1], viewportArray[2], viewportArray[3])

        Core.gl.clearColor(bgColor[0], bgColor[1], bgColor[2], bgColor[3])

        Core.gl.enable(WebGLRenderingContext.SCISSOR_TEST)
        Core.gl.clear(WebGLRenderingContext.COLOR_BUFFER_BIT)
        Core.gl.disable(WebGLRenderingContext.SCISSOR_TEST)

        glMatrix.mat4.lookAt(
            viewMatrix,
            Float32Array(arrayOf(wcCenter[0], wcCenter[1], 10f)),
            Float32Array(arrayOf(wcCenter[0], wcCenter[1], 0f)),
            Float32Array(arrayOf(0f, 1f, 0f)),
        )

        val halfWcWidth = (.5 * wcWidth).toInt()
        val halfWcHeight = halfWcWidth * viewportArray[3] / viewportArray[2]

        glMatrix.mat4.orthoNO(
            projMatrix,
            -halfWcWidth,
            halfWcWidth,
            -halfWcHeight,
            halfWcHeight,
            nearPlane,
            farPlane,
        )

        glMatrix.mat4.multiply(vpMatrix, projMatrix, viewMatrix)

        return vpMatrix
    }
}