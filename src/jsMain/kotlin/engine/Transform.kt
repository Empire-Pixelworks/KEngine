package engine

import engine.js.glMatrix
import org.khronos.webgl.Float32Array
import org.khronos.webgl.get
import org.khronos.webgl.set
import kotlin.math.PI

class Transform(
    private var position: Float32Array = glMatrix.vec2.fromValues(0f, 0f),
    private var scale: Float32Array = glMatrix.vec2.fromValues(1f, 1f),
    private var rotationInRad: Float = 0f,
) {
    fun setX(x: Float) = run { position[0] = x }
    fun setY(y: Float) = run { position[1] = y }

    fun getX() = position[0]
    fun getY() = position[1]

    fun incXPosBy(x: Float) = run { position[0] += x }

    fun setWidth(width: Float) = run { scale[0] = width }
    fun setHeight(height: Float) = run { scale[1] = height }

    fun getWidth() = scale[0]
    fun getHeight() = scale[1]

    fun setPosition(x: Float, y: Float) {
        setX(x)
        setY(y)
    }
    fun setScale(width: Float, height: Float) {
        setWidth(width)
        setHeight(height)
    }

    fun incSizeBy(v: Float) {
        scale[0] += v
        scale[1] += v
    }

    fun setRotationInRad(rotation: Float) = run { rotationInRad = rotation }
    fun setRotationInDeg(deg: Float) = run { rotationInRad = deg * (PI / 180).toFloat() }

    fun incRotationByDegree(deg: Float) = run { rotationInRad += (deg * PI / 180).toFloat() }

    fun getXForm(): Float32Array {
        val matrix = glMatrix.mat4.create()

        glMatrix.mat4.translate(matrix, matrix, glMatrix.vec3.fromValues(position[0], position[1], 0f))
        glMatrix.mat4.rotateZ(matrix, matrix, rotationInRad)
        glMatrix.mat4.scale(matrix, matrix, glMatrix.vec3.fromValues(scale[0], scale[1], 1f))

        return matrix
    }
}