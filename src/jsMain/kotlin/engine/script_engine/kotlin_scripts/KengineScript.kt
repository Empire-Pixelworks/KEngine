package engine.script_engine.kotlin_scripts

import engine.systems.World

abstract class KengineScript(val world: World) {
    abstract fun ready()
    abstract fun update(dt: Float)
}
