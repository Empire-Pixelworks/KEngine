import engine.core.Input
import engine.objects.RenderComponent
import engine.objects.TransformComponent
import engine.script_engine.kotlin_scripts.KengineScript
import engine.script_engine.kotlin_scripts.ScriptFactory
import engine.script_engine.kotlin_scripts.ScriptFactoryRegistry
import engine.systems.World
import kotlin.random.Random

class Script(world: World): KengineScript(world) {
    lateinit var whiteXForm: TransformComponent
    lateinit var redXForm: TransformComponent
    lateinit var pinkXForm: RenderComponent

    override fun ready() {
        whiteXForm = world.getComponentFor<TransformComponent>("WhiteSquare")
        redXForm = world.getComponentFor<TransformComponent>("RedSquare")
        pinkXForm = world.getComponentFor<RenderComponent>("PinkSquare")
    }

    override fun update(dt: Float) {
        if (Input.isKeyPressed(Input.Key.ARROW_RIGHT)) {
            if (whiteXForm.getX() > 30) {
                whiteXForm.setPosition(10f, 60f)
            }
            whiteXForm.incXPosBy(dt)
            pinkXForm.color[1] = Random.nextFloat()
        }

        if (Input.isKeyClicked(Input.Key.ARROW_UP)) {
            whiteXForm.incRotationByDegree(1f)
        }

        if (Input.isKeyPressed(Input.Key.ARROW_DOWN)) {
            if (redXForm.getWidth() > 5) {
                redXForm.setScale(2f, 2f)
            }
            redXForm.incSizeBy(dt)
        }
    }
}

object MainScriptFactory : ScriptFactory {
    override val scriptName = "main"

    override fun create(world: World): KengineScript {
        return Script(world)
    }

    init {
        ScriptFactoryRegistry.register(this)
    }
}