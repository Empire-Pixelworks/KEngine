package engine.shader

import engine.core.Core
import engine.errors.KEngineError
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLUniformLocation

sealed class Shader(private val compiledShader: WebGLProgram, private val shaderVertexPositionAttr: Int) {
    companion object {
        const val PIXEL_COLOR_ATT = "uPixelColor"
        const val MODEL_TRANSFORM_ATT = "uModelTransform"
        const val VIEW_PROJ_TRANSFORM = "uViewProjTransform"
    }

    private val mPixelColor: WebGLUniformLocation = Core.gl.getUniformLocation(compiledShader, PIXEL_COLOR_ATT) ?:
    throw KEngineError("cannot get uniform var $PIXEL_COLOR_ATT")
    private val mModelTransform: WebGLUniformLocation =
        Core.gl.getUniformLocation(compiledShader, MODEL_TRANSFORM_ATT) ?:
        throw KEngineError("cannot get uniform model transform attribute $MODEL_TRANSFORM_ATT")
    private val mViewProjTransform: WebGLUniformLocation =
        Core.gl.getUniformLocation(compiledShader, VIEW_PROJ_TRANSFORM) ?:
        throw KEngineError("cannot get uniform view projection transform attribute $VIEW_PROJ_TRANSFORM")

    open fun activateShader(pixelColor: Array<Float>, vpMatrix: Float32Array) {
        Core.gl.useProgram(compiledShader)
        Core.gl.uniformMatrix4fv(mViewProjTransform, false, vpMatrix)
        Core.gl.enableVertexAttribArray(shaderVertexPositionAttr)
        Core.gl.uniform4fv(mPixelColor, pixelColor)
    }

    open fun loadObjectTransform(modelTransform: Float32Array) {
        Core.gl.uniformMatrix4fv(mModelTransform, false, modelTransform)
    }
}