package engine.objects.renderable

import engine.objects.Component
import engine.shader.Shader

open class RenderComponent(val shader: Shader, val color: Array<Float>): Component
