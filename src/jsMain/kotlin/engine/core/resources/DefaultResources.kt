package engine.core.resources

import engine.Shader
import kotlin.js.Promise

object DefaultResources {
    private const val SIMPLE_VS = "GLSLShaders/SimpleVs.glsl"
    private const val SIMPLE_FS = "GLSLShaders/SimpleFS.glsl"
    lateinit var constColorShader: Shader.SimpleShader

    fun initialize(): Promise<Unit> {
        val defaults = listOf(
            EngineResourceMap.loadResource(
                SIMPLE_VS,
                EngineResourceMap.FileType.TextFile
            ),
            EngineResourceMap.loadResource(
                SIMPLE_FS,
                EngineResourceMap.FileType.TextFile
            ),
        )

        return Promise.all(defaults.toTypedArray()).then {
            val simpleVs = EngineResourceMap.getResource(SIMPLE_VS) as String
            val simpleFs = EngineResourceMap.getResource(SIMPLE_FS) as String
            constColorShader = Shader.simpleShader(simpleVs, simpleFs)
        }
    }
}