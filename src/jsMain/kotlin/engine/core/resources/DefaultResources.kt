package engine.core.resources

import engine.shader.ShaderFactory
import engine.script_engine.Interpreter
import engine.script_engine.Lexer
import engine.script_engine.Parser
import engine.shader.SimpleShader
import engine.shader.TextureShader
import engine.systems.World
import kotlin.js.Promise

object DefaultResources {
    private const val SIMPLE_VS = "GLSLShaders/SimpleVs.glsl"
    private const val SIMPLE_FS = "GLSLShaders/SimpleFS.glsl"
    private const val TEXTURE_VS = "GLSLShaders/TextureVS.glsl"
    private const val TEXTURE_FS = "GLSLShaders/TextureFS.glsl"
    private const val ENTRY_POINT_SCRIPT = "main.kengine"
    lateinit var constColorShader: SimpleShader
    lateinit var textureShader: TextureShader

    fun initialize(): Promise<World> {
        val defaults = listOf(
           SIMPLE_VS, SIMPLE_FS,
            TEXTURE_VS, TEXTURE_FS,
            ENTRY_POINT_SCRIPT,
        ).map {
            EngineResourceMap.loadResource<String>(
                it,
                EngineResourceMap.FileType.TextFile,
            )
        }

        return Promise.all(defaults.toTypedArray()).then { arr ->
            val simpleVs = arr[0]
            val simpleFs = arr[1]
            constColorShader = ShaderFactory.createShader<SimpleShader>(simpleVs, simpleFs)

            val textureVs = arr[2]
            val textureFs = arr[3]
            textureShader = ShaderFactory.createShader<TextureShader>(textureVs, textureFs)

            val text = arr[4]
            val tokens = Lexer.tkz(text)
            val script = Parser(tokens.toMutableList()).start()

            Interpreter().interpret(script)
        }
    }
}