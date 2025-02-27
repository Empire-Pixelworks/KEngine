package engine.systems

import engine.core.Core
import engine.js.glMatrix
import engine.objects.CameraComponent
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLRenderingContext
import org.khronos.webgl.get

object CameraSystem {
    fun setupViewProjection(world: World): Float32Array {
        world.entitiesWith<CameraComponent>().firstOrNull()?.let { e ->
            world.getComponent<CameraComponent>(e)?.let { camera ->
                Core.gl.viewport(camera.viewportArray[0], camera.viewportArray[1], camera.viewportArray[2], camera.viewportArray[3])
                Core.gl.scissor(camera.viewportArray[0], camera.viewportArray[1], camera.viewportArray[2], camera.viewportArray[3])

                Core.gl.clearColor(camera.bgColor[0], camera.bgColor[1], camera.bgColor[2], camera.bgColor[3])

                Core.gl.enable(WebGLRenderingContext.SCISSOR_TEST)
                Core.gl.clear(WebGLRenderingContext.COLOR_BUFFER_BIT)
                Core.gl.disable(WebGLRenderingContext.SCISSOR_TEST)

                glMatrix.mat4.lookAt(
                    camera.viewMatrix,
                    Float32Array(arrayOf(camera.wcCenter[0], camera.wcCenter[1], 10f)),
                    Float32Array(arrayOf(camera.wcCenter[0], camera.wcCenter[1], 0f)),
                    Float32Array(arrayOf(0f, 1f, 0f)),
                )

                val halfWcWidth = (.5 * camera.wcWidth).toInt()
                val halfWcHeight = halfWcWidth * camera.viewportArray[3] / camera.viewportArray[2]

                glMatrix.mat4.orthoNO(
                    camera.projMatrix,
                    -halfWcWidth,
                    halfWcWidth,
                    -halfWcHeight,
                    halfWcHeight,
                    camera.nearPlane,
                    camera.farPlane,
                )

                glMatrix.mat4.multiply(camera.vpMatrix, camera.projMatrix, camera.viewMatrix)

                return camera.vpMatrix
            }
        } ?: throw Exception("Camera unable to be setup!")

    }
}