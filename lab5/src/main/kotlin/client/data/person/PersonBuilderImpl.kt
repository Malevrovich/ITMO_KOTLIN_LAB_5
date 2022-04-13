package client.data.person

import data.checkNull
import share.data.person.Country
import share.data.person.Person
import kotlinx.datetime.LocalDateTime


class PersonBuilderImpl : PersonBuilder {
    private var nationality: Country? = null
    private var birthday: LocalDateTime? = null
    private var name: String? = null

    override fun clear(): PersonBuilder {
        nationality = null
        birthday = null
        name = null

        return this
    }

    override fun setNationality(country: Country?): PersonBuilder {
        nationality = country

        return this
    }

    override fun setBirthday(time: LocalDateTime?): PersonBuilder {
        birthday = time

        return this
    }

    override fun setName(name: String): PersonBuilder {
        if (name.isEmpty()) {
            throw IllegalArgumentException("Поле name не может быть пустым")
        }
        this.name = name

        return this
    }

    override fun setDefault(): PersonBuilder {
        name = "unknown"
        birthday = null
        nationality = null

        return this
    }

    override fun build(): Person {
        checkNull(name, "name")

        val res = Person(name!!, birthday, nationality)

        clear()

        return res
    }

    override fun buildDefault(): Person {
        clear()
        setDefault()

        val res = Person(name!!, birthday, nationality)

        clear()

        return res
    }
}