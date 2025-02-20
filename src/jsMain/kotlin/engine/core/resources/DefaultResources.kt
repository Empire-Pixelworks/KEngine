package engine.core.resources

import engine.Shader
import engine.script_engine.Interpreter
import engine.script_engine.Lexer
import engine.script_engine.Parser
import engine.systems.World
import kotlin.js.Promise

object DefaultResources {
    private const val SIMPLE_VS = "GLSLShaders/SimpleVs.glsl"
    private const val SIMPLE_FS = "GLSLShaders/SimpleFS.glsl"
    private const val ENTRY_POINT_SCRIPT = "main.kengine"
    lateinit var constColorShader: Shader.SimpleShader

    fun initialize(): Promise<World> {
        val defaults = listOf(
            EngineResourceMap.loadResource<String>(
                SIMPLE_VS,
                EngineResourceMap.FileType.TextFile
            ),
            EngineResourceMap.loadResource(
                SIMPLE_FS,
                EngineResourceMap.FileType.TextFile
            ),
            EngineResourceMap.loadResource(
                ENTRY_POINT_SCRIPT,
                EngineResourceMap.FileType.TextFile,
            ),
        )

        return Promise.all(defaults.toTypedArray()).then { arr ->
            val simpleVs = arr[0]
            val simpleFs = arr[1]
            constColorShader = Shader.simpleShader(simpleVs, simpleFs)

            val text = arr[2]
            val tokens = Lexer.tkz(text)
            val script = Parser(tokens.toMutableList()).start()

            Interpreter().interpret(script)
        }
    }
}