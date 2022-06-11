package share.security.encoder_manager

import kotlinx.coroutines.ThreadContextElement
import share.security.encoder.Encoder

interface ContextEncoderManager{
    fun getContextElement(): ThreadContextElement<Encoder>
    fun put(encoder: Encoder): ThreadContextElement<Encoder>
    fun get(): Encoder
}