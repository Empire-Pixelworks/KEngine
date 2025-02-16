package engine.core

import engine.systems.Camera
import engine.systems.RenderSystem
import engine.systems.World
import kotlinx.browser.window
import kotlin.js.Date

object GameLoop {
    const val FPS = 60
    const val MILLI_PER_FRAME = 1000 / FPS

    private var previousTime = 0.0
    private var lagTime = 0.0
    private var currentTime = Date.now()
    private var elapsedTime = 0.0

    private var isLoopRunning = false

    private fun runLoop(world: World) {
        if (isLoopRunning) {
            window.requestAnimationFrame {  runLoop(world) }

            currentTime = Date.now()
            elapsedTime = currentTime - previousTime
            previousTime = currentTime
            lagTime += elapsedTime

            while ((lagTime >= MILLI_PER_FRAME) && isLoopRunning) {
                Input.update()
                world.script?.update(0.5f)
                lagTime -= MILLI_PER_FRAME
            }
            Core.clearCanvas(arrayOf(.9f, .9f, .9f, 1f))
            val camera = Camera.setupViewProjection(world)
            RenderSystem.drawAll(world, camera)
        }
    }

    fun start(world: World) {
        previousTime = Date.now()
        lagTime = 0.0
        isLoopRunning = true
        world.script?.ready()
        window.requestAnimationFrame { runLoop(world) }
    }
}