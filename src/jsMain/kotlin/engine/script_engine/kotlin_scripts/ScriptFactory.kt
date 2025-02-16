package engine.script_engine.kotlin_scripts

import engine.systems.World

interface ScriptFactory {
    val scriptName: String
    fun create(world: World): KengineScript
}

object ScriptFactoryRegistry {
    private val factories = mutableMapOf<String, ScriptFactory>()

    fun register(factory: ScriptFactory) {
        println("Registering script factory: $factory")
        factories[factory.scriptName] = factory
    }

    fun create(name: String, world: World): KengineScript {
        val factory = factories[name]
            ?: error("No script factory registered for name '$name'")
        return factory.create(world)
    }
}