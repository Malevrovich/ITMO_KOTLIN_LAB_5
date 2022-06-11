package share.security.encoder

import share.io.input.Input
import share.io.output.Output
import share.security.ExchangeRole
import share.security.key_provider.LoggedDHKeyExchanger
import share.security.parameters.ParameterSenderImpl
import share.security.parameters.ParametersReceiverImpl

class LoggedAESDHEncoderFactory(val exchangeRole: ExchangeRole): EncoderFactory {
    override fun buildCryptoManager(friendInput: Input, friendOutput: Output): Encoder {
        return AESDHEncoder(
            LoggedDHKeyExchanger(exchangeRole, friendInput, friendOutput),
            ParameterSenderImpl(friendOutput),
            ParametersReceiverImpl(friendInput)
        )
    }
}