package engine.script_engine

import engine.objects.Component
import engine.script_engine.kotlin_scripts.KengineScript
import engine.script_engine.kotlin_scripts.ScriptFactoryRegistry
import engine.systems.World

class Interpreter {
    private val world = World()

    fun interpret(script: Script): World {
        script.elements.forEach { el ->
            when (el) {
                is Obj -> interpretObj(el)
                is Item -> {
                    if (el.identity == "script") {
                        world.script = ScriptFactoryRegistry.create((el.value as StringVal).value, world)
                    }
                }
            }
        }
        return world
    }

    private fun interpretObj(obj: Obj) {
        val entity = world.getOrCreateEntity(obj.identity.entity)
        when (val c = Component.get(obj.identity.component)) {
            null -> error("Component ${obj.identity.component} not found")
            else -> {
                world.attachComponent(entity, Component.getInstance(c, obj.items))
            }
        }
    }
}