package engine.core

import engine.errors.KEngineError
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLBuffer
import org.khronos.webgl.WebGLRenderingContext

object VertexBuffer {
    private var buffer: WebGLBuffer? = null

    private val verticesOfSquare = arrayOf(
        0.5F, 0.5F, 0.0F,
        -0.5f, 0.5F, 0.0F,
        0.5F, -0.5F, 0.0F,
        -0.5F, -0.5F, 0.0F,
    )

    fun initializeBuffer() = setBufferReference()

    fun getBuffer(): WebGLBuffer {
        setBufferReference()
        return buffer ?: throw KEngineError("Buffer not initialized")
    }

    private fun setBufferReference() {
        if (buffer == null) {
            buffer = initialize()
        }
    }

    private fun initialize(): WebGLBuffer {
        val mVBuff = Core.gl.createBuffer() ?: throw KEngineError("There was an error creating the Vertex Buffer")

        Core.gl.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, mVBuff)
        Core.gl.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array(verticesOfSquare), WebGLRenderingContext.STATIC_DRAW)

        return mVBuff
    }
}