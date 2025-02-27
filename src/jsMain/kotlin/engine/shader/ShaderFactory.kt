package engine.shader

import engine.core.Core
import engine.core.VertexBuffer
import engine.errors.KEngineError
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.WebGLShader

object ShaderFactory {
    const val SHADER_POS_ATTR = "aSquareVertexPosition"

    inline fun <reified T: Shader>createShader(vertexShaderSource: String, fragmentShaderSource: String): T {
        val vertexShader = compileShader(vertexShaderSource, WebGLRenderingContext.VERTEX_SHADER)
        val fragmentShader = compileShader(fragmentShaderSource, WebGLRenderingContext.FRAGMENT_SHADER)

        val mCompiledShader = Core.gl.createProgram() ?: throw KEngineError("There was an error creating the Shader program")

        Core.gl.attachShader(mCompiledShader, vertexShader)
        Core.gl.attachShader(mCompiledShader, fragmentShader)
        Core.gl.linkProgram(mCompiledShader)

        if (Core.gl.getProgramParameter(mCompiledShader, WebGLRenderingContext.LINK_STATUS) == false) {
            throw KEngineError("Error Linking Shader")
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

        return when (T::class) {
            SimpleShader::class -> SimpleShader(mCompiledShader, mShaderVertexPositionAttribute) as T
            TextureShader::class -> TextureShader(mCompiledShader, mShaderVertexPositionAttribute) as T
            else -> throw KEngineError("Unsupported shader type")
        }
    }

    fun compileShader(shaderSource: String, shaderType: Int): WebGLShader {
        val compiledShader = Core.gl.createShader(shaderType) ?: throw KEngineError("Shader could not be created")

        Core.gl.shaderSource(compiledShader, shaderSource)
        Core.gl.compileShader(compiledShader)

        if (Core.gl.getShaderParameter(compiledShader, WebGLRenderingContext.COMPILE_STATUS) == false) {
            throw  KEngineError("A shader compiling error occurred: ${Core.gl.getShaderInfoLog(compiledShader)}")
        }

        return compiledShader
    }
}