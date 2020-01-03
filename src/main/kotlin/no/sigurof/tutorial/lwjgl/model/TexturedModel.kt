package no.sigurof.tutorial.lwjgl.model

import no.sigurof.tutorial.lwjgl.context.DefaultSceneContext
import no.sigurof.tutorial.lwjgl.entity.obj.PlainObject
import no.sigurof.tutorial.lwjgl.mesh.MeshManager
import no.sigurof.tutorial.lwjgl.mesh.Vao
import no.sigurof.tutorial.lwjgl.resource.TextureManager
import no.sigurof.tutorial.lwjgl.shaders.settings.impl.TextureShaderSettings
import org.lwjgl.opengl.GL30

class TexturedModel private constructor(
    private val vao: Vao,
    private val tex: Int,
    private val shader: TextureShaderSettings,
    private var objects: List<PlainObject>
) : Model {

    override fun render(globalContext: DefaultSceneContext) {
        shader.usingVaoDo(vao) {
            globalContext.loadUniforms(shader)
            GL30.glActiveTexture(GL30.GL_TEXTURE0)
            GL30.glBindTexture(GL30.GL_TEXTURE_2D, tex)
            for (obj in objects) {
                obj.loadUniforms(shader)
                obj.render(vao)
            }
        }
    }

    override fun cleanShader() {
        shader.cleanUp()
    }

    data class Builder(
        private var vao: Vao? = null,
        private var texName: String = "default",
        private var objects: List<PlainObject> = mutableListOf()
    ) {

        fun withTexture(name: String) = apply { this.texName = name }
        fun withModel(name: String) = apply { this.vao = MeshManager.getMesh(name) }
        fun withObjects(objects: List<PlainObject>) = apply { this.objects = ArrayList(objects) }
        fun build(): TexturedModel {
            val texture = TextureManager.get(texName)
            return TexturedModel(
                vao ?: error("Must have model to build textured model"),
                texture,
                TextureShaderSettings,
                objects
            )
        }
    }
}