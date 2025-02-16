package engine.systems

import engine.objects.Component
import engine.objects.Entity
import engine.script_engine.kotlin_scripts.KengineScript
import kotlin.reflect.KClass

class World {
    private val nameToEntity = mutableMapOf<String, Entity>()
    private var nextEntity: Entity = 1

    var script: KengineScript? = null

    val components = mutableMapOf<Entity, MutableList<Component>>()
    val componentTypes = mutableMapOf<Entity, MutableList<KClass<out Component>>>()

    fun getOrCreateEntity(name: String): Entity =
        nameToEntity[name] ?: run {
            val e = nextEntity++
            nameToEntity[name] = e
            components[e] = mutableListOf()
            componentTypes[e] = mutableListOf()
            e
        }

    fun attachComponent(entity: Entity, component: Component) {
        components[entity]?.add(component)
        componentTypes[entity]?.add(component::class)
    }

    fun entitiesWithAll(requestedComponents: Set<KClass<out Component>>): Set<Entity> =
        componentTypes.filterValues { it.containsAll(requestedComponents) }.keys.toSet()

    inline fun <reified T: Component> entitiesWith(): Set<Entity> =
        components.filterValues { it.any { v -> v is T } }.keys.toSet()

    inline fun <reified T: Component> getComponent(entity: Entity): T? =
        components[entity]?.find { it is T } as T?
}