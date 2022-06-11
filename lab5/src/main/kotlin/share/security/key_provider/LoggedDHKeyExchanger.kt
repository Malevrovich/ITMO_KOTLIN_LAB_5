package share.security.key_provider

import org.slf4j.LoggerFactory
import share.io.input.Input
import share.io.output.Output
import share.security.ExchangeRole

class LoggedDHKeyExchanger(role: ExchangeRole,
                                friendInput: Input,
                                friendOutput: Output,
): DHKeyExchanger(role, friendInput, friendOutput) {

    val logger = LoggerFactory.getLogger(this.javaClass)

    override fun sendPublicKey() {
        logger.debug("Sending public key")
        super.sendPublicKey()
    }

    override fun acceptPublicKey() {
        logger.debug("Accepting public key")
        super.acceptPublicKey()
    }

    override fun processKey() {
        super.processKey()
        logger.debug("Key processed")
    }
}