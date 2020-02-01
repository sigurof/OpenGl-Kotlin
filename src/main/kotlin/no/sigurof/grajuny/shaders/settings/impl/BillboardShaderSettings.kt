package no.sigurof.grajuny.shaders.settings.impl

import no.sigurof.grajuny.entity.Light
import no.sigurof.grajuny.shaders.ShaderManager
import no.sigurof.grajuny.shaders.settings.DefaultShaderSettings
import org.joml.Matrix4f
import org.joml.Vector3f

private const val vtxSource = "/shader/billboard/vertex.shader"
private const val frgSource = "/shader/billboard/fragment.shader"

object BillboardShaderSettings : DefaultShaderSettings {
    override val attributes: List<Pair<Int, String>> = emptyList()
    override val program = ShaderManager.compileProgram(vtxSource, frgSource, attributes)
    private val uniforms = listOf(
        "prjMatrix",
        "viewMatrix",
        "sphereRadius",
        "sphereCenter",
        "cameraPos",
        "lightPos",
        "lightCol",
        "ambient",
        "color",
        "shineDamper",
        "reflectivity",
        "frUseTexture",
        "frPrjMatrix",
        "frViewMatrix",
        "frSphereRadius",
        "frCameraPos",
        "frSphereCenter"
    )
    private val locations = uniforms.map { it to ShaderManager.getUniformLocation(it, program) }.toMap()

    override fun loadTransformationMatrix(transformationMatrix: Matrix4f) {
        return
    }

    override fun loadProjectionMatrix(projectionMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("prjMatrix"), projectionMatrix)
        ShaderManager.loadMatrix(locations.getValue("frPrjMatrix"), projectionMatrix)
    }

    override fun loadViewMatrix(viewMatrix: Matrix4f) {
        ShaderManager.loadMatrix(locations.getValue("viewMatrix"), viewMatrix)
        ShaderManager.loadMatrix(locations.getValue("frViewMatrix"), viewMatrix)
    }

    override fun loadLight(light: Light) {
        ShaderManager.loadVector3(locations.getValue("lightPos"), light.position)
        ShaderManager.loadVector3(locations.getValue("lightCol"), light.color)
        ShaderManager.loadFloat(locations.getValue("ambient"), light.ambient)
    }

    override fun loadSpecularValues(damper: Float, reflectivity: Float) {
        ShaderManager.loadFloat(locations.getValue("shineDamper"), damper)
        ShaderManager.loadFloat(locations.getValue("reflectivity"), reflectivity)
    }

    override fun loadCameraPosition(cameraPosition: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("cameraPos"), cameraPosition)
        ShaderManager.loadVector3(locations.getValue("frCameraPos"), cameraPosition)
    }

    override fun loadColor(color: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("color"), color);
    }

    fun loadSphereCenter(sphereCenter: Vector3f) {
        ShaderManager.loadVector3(locations.getValue("sphereCenter"), sphereCenter)
        ShaderManager.loadVector3(locations.getValue("frSphereCenter"), sphereCenter)
    }

    fun loadSphereRadius(radius: Float) {
        ShaderManager.loadFloat(locations.getValue("sphereRadius"), radius)
        ShaderManager.loadFloat(locations.getValue("frSphereRadius"), radius)
    }

    fun loadUseTexture(useTexture: Boolean) {
        ShaderManager.loadBoolean(locations.getValue("frUseTexture"), useTexture)
    }

}