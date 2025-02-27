package engine.objects

import engine.js.glMatrix
import engine.objects.Component.Companion.getAttrForType
import engine.script_engine.ArrayVal
import engine.script_engine.IntVal
import engine.script_engine.Value
import org.khronos.webgl.Float32Array

data class CameraComponent(private val attrs: Map<String, Value>): Component {
    val wcCenter: Float32Array = Float32Array(
        getAttrForType<ArrayVal>(attrs["center"])?.toFloatArr() ?: throw Exception("'center' attribute not defined for Camera")
    )
    val wcWidth: Int = getAttrForType<IntVal>(attrs["width"])?.value ?: throw Exception("'width' attribute not defined for Camera")
    val viewportArray: Array<Int> = getAttrForType<ArrayVal>(attrs["viewport"])?.toIntArr() ?: throw Exception("'viewport' attribute not defined for Camera")

    val bgColor: Float32Array = Float32Array(arrayOf(.8f, .8f, .8f, 1f))
    val nearPlane: Int = 0
    val farPlane: Int = 1000
    val viewMatrix: Float32Array = glMatrix.mat4.create()
    val projMatrix: Float32Array = glMatrix.mat4.create()
    val vpMatrix: Float32Array = glMatrix.mat4.create()
}