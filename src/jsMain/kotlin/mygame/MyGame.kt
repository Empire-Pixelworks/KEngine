package mygame

import engine.core.GameLoop

interface MyGame {
    fun update() {}
    fun draw() {}
    fun init() {}
    fun start() = GameLoop.start(this)
}