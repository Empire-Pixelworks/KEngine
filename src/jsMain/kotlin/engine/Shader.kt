package engine

import engine.core.Core
import engine.core.VertexBuffer
import engine.errors.KEngineError
import engine.util.Failure
import engine.util.Success
import engine.util.Try
import engine.util.onErr
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLShader
import org.khronos.webgl.WebGLUniformLocation
import org.w3c.xhr.XMLHttpRequest

object Shader {
    private const val SHADER_POS_ATTR = "aSquareVertexPosition"
    private const val PIXEL_COLOR_ATT = "uPixelColor"
    private const val MODEL_TRANSFORM_ATT = "uModelTransform"
    private const val VIEW_PROJ_TRANSFORM = "uViewProjTransform"

    data class SimpleShader(private val compiledShader: WebGLProgram, private val shaderVertexPositionAttr: Int) {
        private val mPixelColor: WebGLUniformLocation = Core.gl.getUniformLocation(compiledShader, PIXEL_COLOR_ATT) ?:
            throw KEngineError("cannot get uniform var $PIXEL_COLOR_ATT")
        private val mModelTransform: WebGLUniformLocation =
            Core.gl.getUniformLocation(compiledShader, MODEL_TRANSFORM_ATT) ?:
                throw KEngineError("cannot get uniform model transform attribute $MODEL_TRANSFORM_ATT")
        private val mViewProjTransform: WebGLUniformLocation =
            Core.gl.getUniformLocation(compiledShader, VIEW_PROJ_TRANSFORM) ?:
                throw KEngineError("cannot get uniform view projection transform attribute $VIEW_PROJ_TRANSFORM")

        fun activateShader(pixelColor: Array<Float>, vpMatrix: Float32Array) {
            Core.gl.useProgram(compiledShader)
            Core.gl.uniformMatrix4fv(mViewProjTransform, false, vpMatrix)
            Core.gl.enableVertexAttribArray(shaderVertexPositionAttr)
            Core.gl.uniform4fv(mPixelColor, pixelColor)
        }

        fun loadObjectTransform(modelTransform: Float32Array) {
            Core.gl.uniformMatrix4fv(mModelTransform, false, modelTransform)
        }
    }

    fun simpleShader(vertexShaderId: String, fragmentShaderId: String): Try<SimpleShader> {
        val vertexShader = loadAndCompileShader(vertexShaderId, WebGLRenderingContext.VERTEX_SHADER).onErr { return Failure(it) }
        val fragmentShader = loadAndCompileShader(fragmentShaderId, WebGLRenderingContext.FRAGMENT_SHADER).onErr { return Failure(it) }

        val mCompiledShader = Core.gl.createProgram() ?: return Failure(KEngineError("There was an error creating the Shader program"))

        Core.gl.attachShader(mCompiledShader, vertexShader)
        Core.gl.attachShader(mCompiledShader, fragmentShader)
        Core.gl.linkProgram(mCompiledShader)

        if (Core.gl.getProgramParameter(mCompiledShader, WebGLRenderingContext.LINK_STATUS) == false) {
            return Failure(KEngineError("Error Linking Shader"))
        }

        val mShaderVertexPositionAttribute = Core.gl.getAttribLocation(mCompiledShader, SHADER_POS_ATTR)

        Core.gl.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, VertexBuffer.getBuffer())

        Core.gl.vertexAttribPointer(
            mShaderVertexPositionAttribute,
            3,
            WebGLRenderingContext.FLOAT,
            false,
            0,
            0
        )

        return Success(SimpleShader(mCompiledShader, mShaderVertexPositionAttribute))
    }

    private fun loadAndCompileShader(filePath: String, shaderType: Int): Try<WebGLShader> {
        val shaderSource = XMLHttpRequest().let {
            it.open("GET", filePath, false)
            Try.tryFrom { it.send() }.onErr { return Failure(it) }
            it.responseText
        }

        val compiledShader = Core.gl.createShader(shaderType) ?: return Failure(KEngineError("Shader from file $filePath could not be created"))

        Core.gl.shaderSource(compiledShader, shaderSource)
        Core.gl.compileShader(compiledShader)

        if (Core.gl.getShaderParameter(compiledShader, WebGLRenderingContext.COMPILE_STATUS) == false) {
            return Failure(KEngineError("A shader compiling error occurred: ${Core.gl.getShaderInfoLog(compiledShader)}"))
        }

        return Success(compiledShader)
    }
}