import engine.core.Input
import engine.objects.AudioComponent
import engine.objects.TransformComponent
import engine.script_engine.kotlin_scripts.KengineScript
import engine.script_engine.kotlin_scripts.ScriptFactory
import engine.script_engine.kotlin_scripts.ScriptFactoryRegistry
import engine.systems.World

class Two(world: World): KengineScript(world) {
    lateinit var another: TransformComponent
    lateinit var bg: AudioComponent

    override fun ready() {
        another = world.getComponentFor<TransformComponent>("AnotherSquare")
        bg = world.getComponentFor<AudioComponent>("Background")
        bg.playBg()
    }

    override fun update(dt: Float) {
        if (Input.isKeyPressed(Input.Key.ARROW_UP)) {
            if (another.getX() > 30) {
                another.setPosition(10f, 60f)
            } else {
                another.incXPosBy(dt)
            }
        }
    }
}

object TwoScriptFactory : ScriptFactory {
    override val scriptName = "two"

    override fun create(world: World): KengineScript {
        return Two(world)
    }

    init {
        ScriptFactoryRegistry.register(this)
    }
}