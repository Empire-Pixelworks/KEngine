package engine.objects

import engine.js.glMatrix
import engine.script_engine.ArrayVal
import engine.script_engine.FloatVal
import engine.script_engine.IntVal
import engine.script_engine.Value
import org.khronos.webgl.Float32Array
import kotlin.reflect.KClass

sealed interface Component {
    companion object {
        private val registry = mutableMapOf<String, KClass<out Component>>()

        init {
            registry["Camera"] = CameraComponent::class
            registry["Render"] = RenderComponent::class
            registry["Transform"] = TransformComponent::class
        }

        fun get(id: String): KClass<out Component>? = registry[id]

        fun getInstance(c: KClass<out Component>, attrs: Map<String, Value>) = when (c) {
            TransformComponent::class -> {
                val position = getAttrForType<ArrayVal>(attrs["position"])?.toFloatArr()
                val scale = getAttrForType<ArrayVal>(attrs["scale"])?.toFloatArr()
                val rotation = getAttrForType<FloatVal>(attrs["rotation"])?.value

                TransformComponent(
                    position =  fromArr(position) ?: glMatrix.vec2.fromValues(0f, 0f),
                    scale = fromArr(scale) ?: glMatrix.vec2.fromValues(1f, 1f),
                    rotationInRad = rotation ?: 0f,
                )
            }
            RenderComponent::class -> {
                val color = getAttrForType<ArrayVal>(attrs["color"])?.toFloatArr()

                RenderComponent(
                    color = color ?: arrayOf(1f, 1f, 1f, 1f),
                )
            }
            CameraComponent::class -> {
                val center = getAttrForType<ArrayVal>(attrs["center"])?.toFloatArr() ?: throw Exception("'center' attribute not defined for Camera")
                val width = getAttrForType<IntVal>(attrs["width"])?.value ?: throw Exception("'width' attribute not defined for Camera")
                val viewport = getAttrForType<ArrayVal>(attrs["viewport"])?.toIntArr() ?: throw Exception("'viewport' attribute not defined for Camera")

                CameraComponent(
                    wcCenter = Float32Array(center),
                    wcWidth = width,
                    viewportArray = viewport
                )
            }
            else -> throw Exception("Undefined Component")
        }

        private fun fromArr(f: Array<Float>?): Float32Array? =
            if (f == null) null else Float32Array(f)

        private inline fun <reified T: Value>getAttrForType(v: Value?): T? {
            return if (v == null) null
            else {
                if (v is T) v
                else throw IllegalArgumentException("Unsupported value type ${v::class}")
            }
        }
    }
}

fun ArrayVal.toFloatArr(): Array<Float> =
    this.values.map { (it as FloatVal).value }.toTypedArray()

fun ArrayVal.toIntArr(): Array<Int> =
    this.values.map { (it as IntVal).value }.toTypedArray()