package engine.core.resources

import org.w3c.files.File
import org.w3c.xhr.ARRAYBUFFER
import org.w3c.xhr.XMLHttpRequest
import org.w3c.xhr.XMLHttpRequestResponseType
import kotlin.js.Promise

object EngineResourceMap {
    enum class FileType {
        KEngineScript,
        TextFile,
        Audio,
    }
    private val resourceMap = mutableMapOf<String, Resource>()

    fun <T>loadResource(fileName: String, fileType: FileType, postProcess: (Any?) -> Promise<Any> = { Promise.resolve(Unit) }): Promise<T> =
        resourceMap[fileName]?.let {
            incAssetCnt(fileName)
            Promise.resolve(it.resource as T)
        }.run {
            Promise { resolve, reject ->
                XMLHttpRequest().let {
                    it.onreadystatechange = { _ ->
                        if ((it.readyState == 4.toShort()) && it.status != 200.toShort()) {
                            reject(Throwable("Error loading file!"))
                        }
                    }
                    it.open("GET", fileName, true)
                    when (fileType) {
                        FileType.Audio -> it.responseType = XMLHttpRequestResponseType.ARRAYBUFFER
                        else -> {}
                    }
                    it.onload = { _ ->
                        when (fileType) {
                            FileType.TextFile, FileType.KEngineScript -> {
                                val resource = Resource(it.responseText)
                                resourceMap[fileName] = resource
                                resolve(resource.resource as T)
                            }
                            FileType.Audio -> {
                                postProcess(it.response).then {
                                    val resource = Resource(it)
                                    resourceMap[fileName] = resource
                                    resolve(resource.resource as T)
                                }
                            }
                        }
                    }
                    it.onerror = { reject(Throwable("There was an error!"))}
                    it.send()
                }
            }
        }

    fun getResource(key: String): Resource? = resourceMap[key]

    fun removeResource(key: String) {
        resourceMap[key]?.let {
            it.usages -= 1
        }
        if (resourceMap.containsKey(key) && resourceMap[key]?.usages == 0) {
            resourceMap.remove(key)
        }
    }

    fun isResourceLoaded(key: String) = resourceMap[key] != null

    private fun incAssetCnt(key: String) {
        resourceMap[key]?.let {
            it.usages += 1
        }
    }
}

data class Resource(
    val resource: Any,
    var usages: Int = 1
)