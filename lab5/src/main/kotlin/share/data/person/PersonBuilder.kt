package share.data.person

import kotlinx.datetime.LocalDateTime
import share.data.Builder
import java.util.*

interface PersonBuilder: Builder<Person> {
    override fun clear(): PersonBuilder

    fun setNationality(country: Country?): PersonBuilder
    fun setBirthday(time: LocalDateTime?): PersonBuilder
    fun setName(name: String?): PersonBuilder

    override fun setProps(props: Properties): Builder<Person>
    override fun setDefault(): PersonBuilder

    override fun copyFrom(el: Person?): PersonBuilder

    override fun build(): Person
    override fun buildDefault(): Person
}