package engine.objects

import engine.js.glMatrix
import org.khronos.webgl.Float32Array

data class CameraComponent(
    val wcCenter: Float32Array,
    val wcWidth: Int,
    val viewportArray: Array<Int>,
    val bgColor: Float32Array = Float32Array(arrayOf(.8f, .8f, .8f, 1f)),
    val nearPlane: Int = 0,
    val farPlane: Int = 1000,
    val viewMatrix: Float32Array = glMatrix.mat4.create(),
    val projMatrix: Float32Array = glMatrix.mat4.create(),
    val vpMatrix: Float32Array = glMatrix.mat4.create(),
): Component