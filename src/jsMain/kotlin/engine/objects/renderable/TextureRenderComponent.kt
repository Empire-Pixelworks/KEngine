package engine.objects.renderable

import engine.core.resources.DefaultResources
import engine.script_engine.Value

data class TextureRenderComponent(private val attrs: Map<String, Value>): RenderComponent(
    DefaultResources.textureShader,
    arrayOf(1f, 1f, 1f, 0f),
)
