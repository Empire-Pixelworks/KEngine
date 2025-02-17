package engine.script_engine.kotlin_scripts

import engine.core.GameLoop
import engine.systems.World

abstract class KengineScript(val world: World) {
    private var stagedScene: String? = null
    abstract fun ready()
    abstract fun update(dt: Float)

    fun loadToNewScene(path: String) {
        stagedScene = path
        GameLoop.stop()
    }

    fun setStagedScene(stagedScene: String) {
        this.stagedScene = stagedScene
    }

    fun getStagedScene() = stagedScene
}
