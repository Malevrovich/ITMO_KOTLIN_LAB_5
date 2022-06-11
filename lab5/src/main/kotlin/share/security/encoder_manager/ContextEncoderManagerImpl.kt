package share.security.encoder_manager

import kotlinx.coroutines.ThreadContextElement
import kotlinx.coroutines.asContextElement
import share.security.encoder.Encoder

class ContextEncoderManagerImpl: ContextEncoderManager {
    var current: Encoder? = null
    val threadLocal: ThreadLocal<Encoder> = ThreadLocal()

    override fun getContextElement(): ThreadContextElement<Encoder> {
        return threadLocal
            .asContextElement(value = current
                ?: throw IllegalArgumentException("Encoder manager must contain any Encoder"))
    }

    override fun put(encoder: Encoder): ThreadContextElement<Encoder> {
        current = encoder
        return threadLocal.asContextElement(current!!)
    }

    override fun get(): Encoder {
        return threadLocal.get()
    }
}