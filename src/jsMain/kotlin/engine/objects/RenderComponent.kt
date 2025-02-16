package engine.objects

import engine.Shader
import engine.core.resources.DefaultResources

data class RenderComponent(
    val shader: Shader.SimpleShader = DefaultResources.constColorShader,
    val color: Array<Float> = arrayOf(1f, 1f, 1f, 1f),
): Component