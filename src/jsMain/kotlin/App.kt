import engine.core.Core
import kotlinx.browser.document
import mygame.Client
import org.khronos.webgl.*
import org.w3c.dom.HTMLCanvasElement

//fun main() {
//    doGlDraw()
//}
//
//fun doGlDraw() {
//    initializeGl()
//}
//
//fun initializeGl() {
//    val canvas = document.getElementById("GLCanvas") as HTMLCanvasElement
//    val gl = (canvas.getContext("webgl") ?:
//    canvas.getContext("experimental-webgl")) as WebGLRenderingContext?
//
//    if (gl != null) {
//        gl.clearColor(0.0F, 0.8F, 0.0F, 1.0F)
//        val program = initSimpleShader(gl, "VertexShader", "FragmentShader")
//        gl.drawSquare(program)
//    } else {
//        document.write("<br><b>WebGL is not supported!</b>")
//    }
//}
//
//fun WebGLRenderingContext.drawSquare(glSimpleShader: WebGLProgram?) {
//    this.clear(WebGLRenderingContext.COLOR_BUFFER_BIT)
//    this.useProgram(glSimpleShader)
//    this.enableVertexAttribArray(this.getAttribLocation(glSimpleShader, "aSquareVertexPosition"))
//    this.drawArrays(WebGLRenderingContext.TRIANGLE_STRIP, 0, 4)
//}
//
//fun initSquareBuffer(gl: WebGLRenderingContext): WebGLBuffer? {
//    val verticesOfASquare = arrayOf(
//        0.5F, 0.5F, 0.0F,
//        -0.5F, 0.5F, 0.0F,
//        0.5F, -0.5F, 0.0F,
//        -0.5F, -0.5F, 0.0F,
//    )
//
//    val buffer = gl.createBuffer()
//    gl.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, buffer)
//    gl.bufferData(WebGLRenderingContext.ARRAY_BUFFER, Float32Array(verticesOfASquare), WebGLRenderingContext.STATIC_DRAW)
//
//    return buffer
//}
//
//fun loadAndCompileShader(gl: WebGLRenderingContext, id: String, shaderType: Int): WebGLShader {
//    val shaderText = document.getElementById(id)!!
//    val shaderSource = shaderText.firstChild!!.textContent ?: throw Exception("Shader $id not found")
//
//    val compiledShader = gl.createShader(shaderType)
//
//    gl.shaderSource(compiledShader, shaderSource)
//    gl.compileShader(compiledShader)
//
//    if (gl.getShaderParameter(compiledShader, WebGLRenderingContext.COMPILE_STATUS) == false) {
//        throw Exception("A shader compiling error occurred: ${gl.getShaderInfoLog(compiledShader)}")
//    }
//
//    return compiledShader ?: throw Exception("Shader $id not found")
//}
//
//fun initSimpleShader(gl: WebGLRenderingContext, vertexShaderId: String, fragmentShaderId: String): WebGLProgram? {
//    val vertexShader = loadAndCompileShader(gl, vertexShaderId, WebGLRenderingContext.VERTEX_SHADER)
//    val fragmentShader = loadAndCompileShader(gl, fragmentShaderId, WebGLRenderingContext.FRAGMENT_SHADER)
//
//    val gSimpleShader = gl.createProgram()
//    gl.attachShader(gSimpleShader, vertexShader)
//    gl.attachShader(gSimpleShader, fragmentShader)
//    gl.linkProgram(gSimpleShader)
//
//    if (gl.getProgramParameter(gSimpleShader, WebGLRenderingContext.LINK_STATUS) == false) {
//        throw Exception("Error Linking Shader")
//    }
//
//    val gShaderVertexPositionAttr = gl.getAttribLocation(gSimpleShader, "aSquareVertexPosition")
//
//    val buffer = initSquareBuffer(gl)
//
//    gl.bindBuffer(WebGLRenderingContext.ARRAY_BUFFER, buffer)
//
//    gl.vertexAttribPointer(
//        gShaderVertexPositionAttr,
//        3,
//        WebGLRenderingContext.FLOAT,
//        false,
//        0,
//        0
//    )
//
//    return gSimpleShader
//}

fun main() {
    Core.initializeGame(Client())
}