package mygame

import engine.Camera
import engine.Renderable
import engine.Shader
import engine.core.Core
import engine.core.GameLoop
import engine.core.Input
import engine.script_engine.Lexer
import engine.util.getOrThrow
import org.khronos.webgl.Float32Array

class Client(): MyGame {
    val camera = Camera(
        Float32Array(arrayOf(20f, 60f)),
        20,
        arrayOf(20, 40, 600, 300),
    )

    val simpleShader = Shader
        .simpleShader("GLSLShaders/SimpleVS.glsl", "GLSLShaders/SimpleFS.glsl")
        .getOrThrow()

    val whiteSq = Renderable(simpleShader, arrayOf(1f,1f,1f,1f))
    val redSq = Renderable(simpleShader, arrayOf(1f, 0f, 0f, 1f))

    init {
        val ke = """
            thingOne = "some cool string"
            thingTwo = 23.44
            [thing]
            ttt = -22
            tsf = [ "one", "two", "three"]
        """.trimIndent()
        println("checking things...")
        println(Lexer.tkz(ke))
        camera.bgColor = Float32Array(arrayOf(.8f, .8f, .8f, 1f))

        whiteSq.xForm.setPosition(20f, 60f)
        whiteSq.xForm.setRotationInRad(.2f)
        whiteSq.xForm.setScale(5f, 5f)

        redSq.xForm.setPosition(20f, 60f)
        redSq.xForm.setScale(2f, 2f)

        GameLoop.start(this)
    }

    override fun update() {
        val whiteXForm = whiteSq.xForm
        val delta = .05f
        if (Input.isKeyPressed(Input.Key.ARROW_RIGHT)) {
            if (whiteXForm.getX() > 30) {
                whiteXForm.setPosition(10f, 60f)
            }
            whiteXForm.incXPosBy(delta)
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
