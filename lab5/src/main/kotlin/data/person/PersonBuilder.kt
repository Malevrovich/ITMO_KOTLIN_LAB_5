package data.person

import kotlinx.datetime.LocalDateTime

interface PersonBuilder {
    fun clear(): PersonBuilder

    fun setNationality(country: Country?): PersonBuilder
    fun setBirthday(time: LocalDateTime?): PersonBuilder
    fun setName(name: String): PersonBuilder

    fun setDefault(): PersonBuilder

    fun build(): Person
    fun buildDefault(): Person
}