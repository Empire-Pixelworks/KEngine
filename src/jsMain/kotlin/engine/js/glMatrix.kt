package engine.js

import org.khronos.webgl.Float32Array

@JsModule("gl-matrix")
@JsNonModule
external object glMatrix {
    object mat4 {
        fun create(): Float32Array
        fun identity(out: Float32Array): Float32Array
        fun translate(out: Float32Array, a: Float32Array, v: Float32Array): Float32Array
        fun rotateZ(out: Float32Array, a: Float32Array, rad: Float): Float32Array
        fun scale(out: Float32Array, a: Float32Array, v: Float32Array): Float32Array
        fun lookAt(out: Float32Array, eye: Float32Array, center: Float32Array, up: Float32Array): Float32Array
        fun orthoNO(out: Float32Array, left: Int, right: Int, bottom: Int, top: Int, near: Int, far: Int): Float32Array
        fun multiply(out: Float32Array, a: Float32Array, b: Float32Array): Float32Array
    }
    object vec3 {
        fun create(): Float32Array
        fun fromValues(x: Float, y: Float, z: Float): Float32Array
    }

    object vec2 {
        fun create(): Float32Array
        fun fromValues(x: Float, y: Float): Float32Array
    }
}