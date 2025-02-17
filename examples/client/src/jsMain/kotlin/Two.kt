import engine.core.Input
import engine.objects.TransformComponent
import engine.script_engine.kotlin_scripts.KengineScript
import engine.script_engine.kotlin_scripts.ScriptFactory
import engine.script_engine.kotlin_scripts.ScriptFactoryRegistry
import engine.systems.World

class Two(world: World): KengineScript(world) {
    lateinit var another: TransformComponent

    override fun ready() {
        another = world.getComponentFor<TransformComponent>("AnotherSquare")
    }

    override fun update(dt: Float) {
        println("updating in the two!")
        if (Input.isKeyPressed(Input.Key.ARROW_UP)) {
            println("up!")
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