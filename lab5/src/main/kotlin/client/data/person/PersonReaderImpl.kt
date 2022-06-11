package client.data.person

import kotlinx.datetime.toKotlinLocalDateTime
import share.data.person.Person
import share.data.person.PersonBuilder
import share.io.input.Input
import share.io.output.Output
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class PersonReaderImpl(
    private val personBuilder: PersonBuilder
): PersonReader {

    override fun askName(inp: Input, out: Output, personBuilder: PersonBuilder) {
        while(true){
            out.print("Введите поле Person.name: ")

            try{
                personBuilder.setName(inp.nextLine())

            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            }

            break
        }
    }

    override fun askBirthday(inp: Input, out: Output, personBuilder: PersonBuilder) {
        while(true){
            out.print("Введите поле Person.birthday: ")

            val format = "HH:mm dd.MM.yyyy"
            val formatter = DateTimeFormatter.ofPattern(format)
            out.println("Используемый формат $format")

            val s = inp.nextLine()

            if(s.isBlank()){
                personBuilder.setBirthday(null)
                break
            }

            if(s.trim().split(" ").size != 2){
                out.println("Поле Person.birthday должно содержать два слова")
                continue
            }

            try {
                personBuilder.setBirthday(LocalDateTime.parse(s.trim(), formatter).toKotlinLocalDateTime())
            } catch (e: DateTimeParseException){
                out.println(s.trim())
                out.println("Поле Person.birthday должно соответствовать формату")
                continue
            }
            break
        }
    }

    override fun askPerson(inp: Input, out: Output): Person {
        personBuilder.clear()

        askName(inp, out, personBuilder)
        askBirthday(inp, out, personBuilder)

//        personBuilder.setNationality(askEnum<Country>(inp, out, "nationality", true))

        return personBuilder.build()
    }
}