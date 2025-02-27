package engine.shader

import engine.core.Core
import engine.core.VertexBuffer
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLProgram
import org.khronos.webgl.WebGLRenderingContext

data class TextureShader(private val compiledShader: WebGLProgram, private val shaderVertexPositionAttr: Int)
    :Shader(compiledShader, shaderVertexPositionAttr) {

    val shaderTextureCoordAttribute = Core.gl.getAttribLocation(compiledShader, "aTextureCoordinate")

    override fun activateShader(pixelColor: Array<Float>, vpMatrix: Float32Array) {
        super.activateShader(pixelColor, vpMatrix)

        Core.gl.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, VertexBuffer.getTextureBuffer())
        Core.gl.enableVertexAttribArray(shaderVertexPositionAttr)
        Core.gl.vertexAttribPointer(
            shaderTextureCoordAttribute,
            2,
            WebGLRenderingContext.FLOAT,
            false,
            0,
            0
        )
    }

}