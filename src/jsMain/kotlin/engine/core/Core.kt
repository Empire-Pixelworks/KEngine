package engine.core

import engine.core.resources.DefaultResources
import engine.core.resources.EngineResourceMap
import engine.errors.KEngineCanvasNotFound
import engine.errors.WebGlInvalidArraySizeError
import engine.errors.WebGlNotSupportedError
import engine.script_engine.Interpreter
import engine.script_engine.Lexer
import engine.script_engine.Parser
import kotlinx.browser.document
import org.khronos.webgl.WebGLRenderingContext
import org.w3c.dom.HTMLCanvasElement

private const val CANVAS_ID = "GLCanvas"
object Core {
    val gl: WebGLRenderingContext

    init {
        gl = initializeWebGl()
        VertexBuffer.initializeBuffer()
    }

    fun initializeGame() {
        DefaultResources.initialize().then { world ->
            GameLoop.start(world)
        }
    }

    fun initializeNewScene(path: String) {
        EngineResourceMap.loadResource(path, EngineResourceMap.FileType.TextFile)
            .then {
                val text = EngineResourceMap.getResource(path) as String
                val tokens = Lexer.tkz(text)
                val script = Parser(tokens.toMutableList()).start()

                val world = Interpreter().interpret(script)
                GameLoop.start(world)
            }
    }

    private fun initializeWebGl(): WebGLRenderingContext {
        val canvas = (
                document.getElementById(CANVAS_ID) as HTMLCanvasElement?
                ) ?: throw KEngineCanvasNotFound(CANVAS_ID)

        return when (val context = canvas.getContext("webgl")) {
            null -> {
                when (val experimentalContext = canvas.getContext("experimental-webgl")) {
                    null -> throw WebGlNotSupportedError
                    else -> experimentalContext as WebGLRenderingContext
                }
            }
            else -> context as WebGLRenderingContext
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