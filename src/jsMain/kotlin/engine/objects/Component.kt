package engine.objects

import engine.objects.renderable.RenderComponent
import engine.script_engine.*
import org.khronos.webgl.Float32Array
import kotlin.js.Promise
import kotlin.reflect.KClass

interface Component {
    fun allComponentsLoaded(): Promise<Unit> = Promise.resolve(Unit)

    companion object {
        private val registry = mutableMapOf<String, KClass<out Component>>()

        init {
            registry["Camera"] = CameraComponent::class
            registry["Render"] = RenderComponent::class
            registry["Transform"] = TransformComponent::class
            registry["Audio"] = AudioComponent::class
        }

        fun get(id: String): KClass<out Component>? = registry[id]

        fun getInstance(c: KClass<out Component>, attrs: Map<String, Value>) = when (c) {
            TransformComponent::class -> TransformComponent(attrs)
            RenderComponent::class -> RenderComponent(attrs)
            CameraComponent::class -> CameraComponent(attrs)
            AudioComponent::class -> AudioComponent(attrs)
            else -> throw Exception("Undefined Component")
        }

        fun fromArr(f: Array<Float>?): Float32Array? =
            if (f == null) null else Float32Array(f)

        inline fun <reified T: Value>getAttrForType(v: Value?): T? {
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