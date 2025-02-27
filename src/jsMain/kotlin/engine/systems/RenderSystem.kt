package engine.systems

import engine.core.Core
import engine.objects.TransformComponent
import engine.objects.renderable.BasicRenderComponent
import engine.objects.renderable.RenderComponent
import engine.objects.renderable.TextureRenderComponent
import org.khronos.webgl.Float32Array
import org.khronos.webgl.WebGLRenderingContext

object RenderSystem {
    fun drawAll(world: World, vpMatrix: Float32Array) {
        world.entitiesWithAll(
            setOf(TransformComponent::class, RenderComponent::class)
        ).forEach { entity ->
            val t = world.getComponent<TransformComponent>(entity) ?: throw Exception("Transform Component not found for entity: $entity")
            val r = world.getComponent<RenderComponent>(entity) ?: throw Exception("RenderComponent not found for entity: $entity")
            when(r) {
                is TextureRenderComponent -> textureRender(r)
            }
            basicRender(t, r, vpMatrix)
        }
    }

    private fun basicRender(t: TransformComponent, r: RenderComponent, vpMatrix: Float32Array) {
        r.shader.activateShader(r.color, vpMatrix)
        r.shader.loadObjectTransform(t.xForm())
        Core.gl.drawArrays(WebGLRenderingContext.TRIANGLE_STRIP, 0, 4)
    }

    private fun textureRender(tr: TextureRenderComponent) {

    }
}