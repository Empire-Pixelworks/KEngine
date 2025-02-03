package engine.core.resources

import kotlin.js.Promise

object EngineResourceMap {
    private val resourceMap = mutableMapOf<String, Any>()

    fun loadResource(key: String, f: () -> Promise<Any>) = f().then { resourceMap[key] = it }

    fun getResource(key: String) = resourceMap[key]

    fun removeResource(key: String) = resourceMap.remove(key)
}