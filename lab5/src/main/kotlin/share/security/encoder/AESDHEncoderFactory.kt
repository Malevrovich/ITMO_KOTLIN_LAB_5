package share.security.encoder

import share.io.input.Input
import share.io.output.Output
import share.security.ExchangeRole
import share.security.key_provider.DHKeyExchanger
import share.security.parameters.ParameterSenderImpl
import share.security.parameters.ParametersReceiverImpl

class AESDHEncoderFactory(val exchangeRole: ExchangeRole): EncoderFactory {
    override fun buildCryptoManager(friendInput: Input, friendOutput: Output): Encoder {
        return AESDHEncoder(DHKeyExchanger(exchangeRole, friendInput, friendOutput),
                            ParameterSenderImpl(friendOutput),
                            ParametersReceiverImpl(friendInput))
    }
}