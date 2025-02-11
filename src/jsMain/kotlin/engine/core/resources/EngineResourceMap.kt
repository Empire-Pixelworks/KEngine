package engine.core.resources

import org.w3c.xhr.XMLHttpRequest
import kotlin.js.Promise

object EngineResourceMap {
    enum class FileType {
        KEngineScript,
        TextFile,
    }
    private val resourceMap = mutableMapOf<String, Any>()

    fun loadResource(fileName: String, fileType: FileType): Promise<Unit> =
        if (resourceMap.containsKey(fileName)) {
            Promise.resolve(Unit)
        } else {
            Promise { resolve, reject ->
                XMLHttpRequest().let {
                    it.onreadystatechange = { _ ->
                        if ((it.readyState == 4.toShort()) && it.status != 200.toShort()) {
                            reject(Throwable("Error loading file!"))
                        }
                    }
                    it.open("GET", fileName, true)
                    it.onload = { _ ->
                        if (fileType == FileType.TextFile) {
                            resourceMap[fileName] = it.responseText
                            resolve(Unit)
                        } else {
                            reject(Throwable("Unsupported FileType (For Now)"))
                        }
                    }
                    it.onerror = { reject(Throwable("There was an error!"))}
                    it.send()
                }
            }
        }

    fun getResource(key: String): Any? = resourceMap[key]

    fun removeResource(key: String): Any? = resourceMap.remove(key)
}