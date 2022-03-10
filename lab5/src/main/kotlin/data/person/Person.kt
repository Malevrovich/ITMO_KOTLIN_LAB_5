package data.person

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Person(val name: String, val birthday: LocalDateTime?, val nationality: Country?)