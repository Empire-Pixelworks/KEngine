package engine.core

import kotlinx.browser.window
import mygame.MyGame
import kotlin.js.Date

object GameLoop {
    const val FPS = 60
    const val MILLI_PER_FRAME = 1000 / FPS

    private var previousTime = 0.0
    private var lagTime = 0.0
    private var currentTime = Date.now()
    private var elapsedTime = 0.0

    private var isLoopRunning = false

    private fun runLoop(myGame: MyGame) {
        if (isLoopRunning) {
            window.requestAnimationFrame {  runLoop(myGame) }

            currentTime = Date.now()
            elapsedTime = currentTime - previousTime
            previousTime = currentTime
            lagTime += elapsedTime

            while ((lagTime >= MILLI_PER_FRAME) && isLoopRunning) {
                Input.update()
                myGame.update()
                lagTime -= MILLI_PER_FRAME
            }
            myGame.draw()
        }
    }

    fun start(myGame: MyGame) {
        previousTime = Date.now()
        lagTime = 0.0
        isLoopRunning = true
        window.requestAnimationFrame { runLoop(myGame) }
    }
}