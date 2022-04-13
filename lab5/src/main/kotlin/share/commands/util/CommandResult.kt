package share.commands.util

import kotlinx.serialization.Serializable

@Serializable
data class CommandResult(val stop: Boolean, val out: String = "")