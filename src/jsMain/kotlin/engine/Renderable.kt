package engine

import engine.core.Core
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLRenderingContext

class Renderable(private val shader: Shader.SimpleShader, private var color: Array<Float> = arrayOf(1F,1F,1F,1F)) {
    val xForm = Transform()

    fun setColor(newColor: Array<Float>) {
        color = newColor
    }

    fun getColor(): Array<Float> = color

    fun draw(vpMatrix: Float32Array) {
        shader.activateShader(color, vpMatrix)
        shader.loadObjectTransform(xForm.getXForm())
        Core.gl.drawArrays(WebGLRenderingContext.TRIANGLE_STRIP, 0, 4)
    }
}