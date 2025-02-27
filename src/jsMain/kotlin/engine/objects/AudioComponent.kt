package engine.objects

import engine.core.resources.EngineResourceMap
import engine.objects.Component.Companion.getAttrForType
import engine.script_engine.StringVal
import engine.script_engine.Value
import kotlinx.browser.window
import kotlin.js.Promise

data class AudioComponent(private val attrs: Map<String, Value>): Component {
    private val clipName: String = getAttrForType<StringVal>(attrs["audio"])?.value ?: throw Exception("'audio' attribute not defined for Audio")

    private var bgAudioNode: dynamic = null
    private val audioContext = createAudioContext()
    private lateinit var audio: Any
    private val isLoaded: Promise<Unit>

    init {
        println("am I here?")
        isLoaded = EngineResourceMap.loadResource<Any>(
            clipName,
            EngineResourceMap.FileType.Audio
        ) { response ->
            try {
                println("so here?")
                audioContext.decodeAudioData(response, {}, {err -> println(err)})
            } catch (err: Throwable) {
                throw Exception("There was an error decoding data: $err")
            }
        }
            .then {
                audio = it
                Promise.resolve(Unit)
            }
    }

    override fun allComponentsLoaded(): Promise<Unit> = isLoaded

    private fun createAudioContext(): dynamic {
        val w = window.asDynamic()
        val ctor = w.AudioContext ?: w.webkitAudioContext
        return if (ctor != null) js("new ctor()") else null
    }

    fun play() {
        val sourceNode = audioContext.createBufferSource()
        sourceNode.buffer = audio
        sourceNode.connect(audioContext.destination)
        sourceNode.start(0)
    }

    fun playBg() {
        if (bgAudioNode == null) {
            bgAudioNode = audioContext.createBufferSource()
            bgAudioNode.buffer = audio
            bgAudioNode.connect(audioContext.destination)
            bgAudioNode.loop = true
            bgAudioNode.start(0)
        }

    }

    fun stopBg() {
        if (bgAudioNode != null) {
            bgAudioNode.stop(0)
            bgAudioNode = null
        }
    }
}
