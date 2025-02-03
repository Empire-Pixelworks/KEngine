package engine.core

import engine.errors.KEngineCanvasNotFound
import engine.errors.WebGlInvalidArraySizeError
import engine.errors.WebGlNotSupportedError
import engine.util.Failure
import engine.util.Success
import engine.util.Try
import engine.util.getOrThrow
import kotlinx.browser.document
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement

private const val CANVAS_ID = "GLCanvas"
object Core {
    val gl: WebGLRenderingContext

    init {
       gl = initializeWebGl().getOrThrow()
       VertexBuffer.initializeBuffer()
    }

    private fun initializeWebGl(): Try<WebGLRenderingContext> {
        val canvas = (
                document.getElementById(CANVAS_ID) as HTMLCanvasElement?
                ) ?: return Failure(KEngineCanvasNotFound(CANVAS_ID))

        return when (val context = canvas.getContext("webgl")) {
            null -> {
                when (val experimentalContext = canvas.getContext("experimental-webgl")) {
                    null -> Failure(WebGlNotSupportedError)
                    else -> Success(experimentalContext as WebGLRenderingContext)
                }
            }
            else -> Success(context as WebGLRenderingContext)
        }
    }

    fun clearCanvas(color: Array<Float>) {
        if (color.size != 4) {
            throw WebGlInvalidArraySizeError(color.size, 4)
        }
        gl.clearColor(color[0], color[1], color[2], color[3])
        gl.clear(WebGLRenderingContext.COLOR_BUFFER_BIT)
    }
}