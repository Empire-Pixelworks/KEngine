package mygame

import engine.objects.Camera
import engine.objects.Renderable
import engine.core.Core
import engine.core.Input
import engine.core.resources.DefaultResources
import engine.script_engine.Lexer
import engine.script_engine.Parser
import org.khronos.webgl.Float32Array

class Client(): MyGame {
    private val camera = Camera(
        Float32Array(arrayOf(20f, 60f)),
        20,
        arrayOf(20, 40, 600, 300),
    )
    private lateinit var whiteSq: Renderable
    private lateinit var redSq: Renderable

    override fun init() {
        val simpleShader = DefaultResources.constColorShader
        whiteSq = Renderable(simpleShader, arrayOf(1f,1f,1f,1f))
        redSq = Renderable(simpleShader, arrayOf(1f, 0f, 0f, 1f))

        camera.bgColor = Float32Array(arrayOf(.8f, .8f, .8f, 1f))

        whiteSq.xForm.setPosition(20f, 60f)
        whiteSq.xForm.setRotationInRad(.2f)
        whiteSq.xForm.setScale(5f, 5f)

        redSq.xForm.setPosition(20f, 60f)
        redSq.xForm.setScale(2f, 2f)

        val testies = """
            aValue = 12
            
            [myFirstObject]
            firstObjValue = true
            secondObjectValue = [1,2,3,4]
            third = "some cool text"
            
            [myFirstObject.childOne]
            first = 24.432
            
            [anotherObj]
            first = "thing one"
            second = 23.4
            third = -2353.45
        """.trimIndent()

        val tokens = Lexer.tkz(testies)
        val elements = Parser(tokens.toMutableList()).start()
        elements.forEach { println(it) }
    }

    override fun update() {
        val whiteXForm = whiteSq.xForm
        val delta = .05f
        if (Input.isKeyPressed(Input.Key.ARROW_RIGHT)) {
            println("click")
            if (whiteXForm.getX() > 30) {
                whiteXForm.setPosition(10f, 60f)
                println(whiteSq.xForm.getX())
            }
            whiteXForm.incXPosBy(delta)
            println(whiteSq.xForm.getX())
        }

        if (Input.isKeyClicked(Input.Key.ARROW_UP)) {
            whiteXForm.incRotationByDegree(1f)
        }

        if (Input.isKeyPressed(Input.Key.ARROW_DOWN)) {
            val redXForm = redSq.xForm
            if (redXForm.getWidth() > 5) {
                redXForm.setScale(2f, 2f)
            }
            redXForm.incSizeBy(delta)
        }
    }

    override fun draw() {
        Core.clearCanvas(arrayOf(.9f, .9f, .9f, 1f))

        val vpMatrix = camera.setupViewProjection()

        whiteSq.draw(vpMatrix)
        redSq.draw(vpMatrix)
    }
}
