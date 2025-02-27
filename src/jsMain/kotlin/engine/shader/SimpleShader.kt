package engine.shader

import org.khronos.webgl.WebGLProgram

data class SimpleShader(private val compiledShader: WebGLProgram, private val shaderVertexPositionAttr: Int):
    Shader(compiledShader, shaderVertexPositionAttr)