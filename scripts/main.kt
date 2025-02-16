import engine.core.Input
import engine.objects.Entity
import engine.objects.TransformComponent
import engine.script_engine.kotlin_scripts.KengineScript
import engine.script_engine.kotlin_scripts.ScriptFactory
import engine.script_engine.kotlin_scripts.ScriptFactoryRegistry
import engine.systems.World
import kotlin.properties.Delegates

class Script(world: World): KengineScript(world) {
    var whiteEntity by Delegates.notNull<Int>()
    var redEntity by Delegates.notNull<Int>()

    lateinit var whiteXForm: TransformComponent
    lateinit var redXForm: TransformComponent

    override fun ready() {
        println("ready!")
        whiteEntity = world.getOrCreateEntity("WhiteSquare")
        redEntity = world.getOrCreateEntity("RedSquare")

        whiteXForm = world.getComponent<TransformComponent>(whiteEntity) ?: throw Exception("No TransformComponent found")
        redXForm = world.getComponent<TransformComponent>(redEntity) ?: throw Exception("No TransformComponent found")
    }

    override fun update(dt: Float) {
        if (Input.isKeyPressed(Input.Key.ARROW_RIGHT)) {
            if (whiteXForm.getX() > 30) {
                whiteXForm.setPosition(10f, 60f)
            }
            whiteXForm.incXPosBy(dt)
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
        println("Hello World!")
        ScriptFactoryRegistry.register(this)
    }
}