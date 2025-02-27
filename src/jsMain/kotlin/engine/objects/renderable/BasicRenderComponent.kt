package engine.objects.renderable

import engine.core.resources.DefaultResources
import engine.objects.Component.Companion.getAttrForType
import engine.objects.toFloatArr
import engine.script_engine.ArrayVal
import engine.script_engine.Value

data class BasicRenderComponent(private val attrs: Map<String, Value>): RenderComponent(
    DefaultResources.constColorShader,
    getAttrForType<ArrayVal>(attrs["color"])?.toFloatArr() ?: arrayOf(1f, 1f, 1f, 1f)
)

