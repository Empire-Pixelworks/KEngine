import engine.core.Input
import engine.objects.AudioComponent
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
    lateinit var bg: AudioComponent
    lateinit var jump: AudioComponent

    override fun ready() {
        whiteXForm = world.getComponentFor<TransformComponent>("WhiteSquare")
        redXForm = world.getComponentFor<TransformComponent>("RedSquare")
        pinkXForm = world.getComponentFor<RenderComponent>("PinkSquare")
        bg = world.getComponentFor<AudioComponent>("Background")
        jump = world.getComponentFor<AudioComponent>("Jump")
    }

    override fun update(dt: Float) {
        if (Input.isKeyPressed(Input.Key.ARROW_RIGHT)) {
            bg.playBg()
            if (whiteXForm.getX() > 30) {
                bg.stopBg()
                loadToNewScene("sceneTwo.kengine")
            }
            whiteXForm.incXPosBy(dt)
            pinkXForm.color[1] = Random.nextFloat()
        }

        if (Input.isKeyClicked(Input.Key.ARROW_UP)) {
            jump.play()
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