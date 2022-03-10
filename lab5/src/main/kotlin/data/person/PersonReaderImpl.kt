package data.person

import data.askEnum
import kotlinx.datetime.toKotlinLocalDateTime
import org.kodein.di.DI
import org.kodein.di.instance
import java.io.EOFException
import java.io.OutputStream
import java.io.PrintStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class PersonReaderImpl(
    private val personBuilder: PersonBuilder
): PersonReader {

    override fun askName(inp: Scanner, out: PrintStream, personBuilder: PersonBuilder) {
        val del = inp.delimiter()
        inp.useDelimiter("\n")

        while(true){
            out.print("Введите поле Person.name: ")

            if(!inp.hasNextLine()){
                throw EOFException()
            }

            try{
                personBuilder.setName(inp.nextLine())

            } catch (e: IllegalArgumentException){
                out.println(e.message)
                continue
            }

            break
        }

        inp.useDelimiter(del)
    }

    override fun askBirthday(inp: Scanner, out: PrintStream, personBuilder: PersonBuilder) {
        val del = inp.delimiter()
        inp.useDelimiter("\n")

        while(true){
            out.print("Введите поле Person.birthday: ")

            val format = "HH:mm dd.MM.yyyy"
            val formatter = DateTimeFormatter.ofPattern(format)
            out.println("Используемый формат $format")

            if(!inp.hasNextLine()){
                throw EOFException()
            }

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

        inp.useDelimiter(del)
    }

    override fun askPerson(inp: Scanner, out: PrintStream): Person{
        personBuilder.clear()

        askName(inp, out, personBuilder)
        askBirthday(inp, out, personBuilder)

        personBuilder.setNationality(askEnum<Country>(inp, out, "nationality", true))

        return personBuilder.build()
    }
}