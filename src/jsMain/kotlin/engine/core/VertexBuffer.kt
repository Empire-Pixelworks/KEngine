package engine.core

import engine.errors.KEngineError
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLBuffer
import org.khronos.webgl.WebGLRenderingContext

object VertexBuffer {
    private var buffer: WebGLBuffer? = null
    private var textureCoordBuffer: WebGLBuffer? = null

    private val verticesOfSquare = arrayOf(
        0.5F, 0.5F, 0.0F,
        -0.5f, 0.5F, 0.0F,
        0.5F, -0.5F, 0.0F,
        -0.5F, -0.5F, 0.0F,
    )
    private val textureCoordinates = arrayOf(
        1.0f, 1.0f,
        0.0f, 1.0f,
        1.0f, 0.0f,
        0.0f, 0.0f,
    )

    fun initializeBuffer() = setBufferReference()

    fun getBuffer(): WebGLBuffer {
        setBufferReference()
        return buffer ?: throw KEngineError("Buffer not initialized")
    }

    fun getTextureBuffer(): WebGLBuffer {
        setBufferReference()
        return textureCoordBuffer ?: throw KEngineError("Texture Buffer not initialized")
    }

    private fun setBufferReference() {
        if (buffer == null || textureCoordBuffer == null) {
            initialize()
        }
    }

    private fun initialize() {
        fun initBuffer(buff: WebGLBuffer?, coordBuff: Array<Float>) {
            Core.gl.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, buff)
            Core.gl.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array(coordBuff), WebGLRenderingContext.STATIC_DRAW)
        }

        buffer = Core.gl.createBuffer() ?: throw KEngineError("There was an error creating the Vertex Buffer")
        initBuffer(buffer, verticesOfSquare)

        textureCoordBuffer = Core.gl.createBuffer() ?: throw KEngineError("There was an error creating the Vertex Buffer")
        initBuffer(textureCoordBuffer, textureCoordinates)
    }

}