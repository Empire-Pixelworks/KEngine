package engine.errors

sealed class EngineError(msg: String): Exception(msg)
object WebGlNotSupportedError: EngineError("WebGL is not supported in this browser")
class WebGlInvalidArraySizeError(size: Int, expected: Int): EngineError("Expected size of $expected, but got $size")
class KEngineCanvasNotFound(canvasId: String): EngineError("Canvas with id: $canvasId not found")
class KEngineElementNotFound(elementId: String): EngineError("Element with id: $elementId not found")
class KEngineError(msg: String): EngineError(msg)
