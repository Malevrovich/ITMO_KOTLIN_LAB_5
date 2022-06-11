package share.data.person

import data.checkNull
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import share.data.Builder
import java.time.format.DateTimeFormatter
import java.util.*


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

    override fun setName(name: String?): PersonBuilder {
        if (name != null) {
            if (name.isEmpty()) {
                throw IllegalArgumentException("Поле name не может быть пустым")
            }
        }
        this.name = name ?: return this

        return this
    }

    override fun setProps(props: Properties): Builder<Person> {
        setBirthday(
            (getOrNull(props, "birthday") as String?)?.let {
                val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                java.time.LocalDateTime.parse(
                    it,
                    pattern
                ).toKotlinLocalDateTime()
            }
        )

        val nationality = getOrNull(props, "nationality") as String?
        setNationality(nationality?.let { Country.valueOf(it) })

        setName(getOrNull(props, "name") as String?)

        return this
    }

    override fun setDefault(): PersonBuilder {
        name = "unknown"
        birthday = null
        nationality = null

        return this
    }

    override fun copyFrom(el: Person?): PersonBuilder {
        if(el == null) {
            return this
        }

        setBirthday(el.birthday)
        setName(el.name)
        setNationality(el.nationality)

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