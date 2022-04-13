package data.person

import client.data.person.PersonBuilder
import client.data.person.PersonBuilderImpl
import client.data.person.PersonReaderImpl
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import share.data.person.Country
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.EOFException
import java.io.PrintStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

internal class PersonReaderImplTest {

    val di = DI{
        bindProvider<PersonBuilder> { PersonBuilderImpl() }
    }

    lateinit var personReaderImpl: PersonReaderImpl

    @BeforeTest
    fun setup(){
        personReaderImpl = PersonReaderImpl(di)
    }

    private fun byteArrayInputStream(s: String): ByteArrayInputStream {
        return ByteArrayInputStream(s.toByteArray())
    }

    @Test
    fun askName() {
        val personBuilder: PersonBuilder by di.instance()

        fun test1(){
            val inp = byteArrayInputStream("name")
            val out = ByteArrayOutputStream()

            personBuilder.setDefault()

            personReaderImpl.askName(Scanner(inp), PrintStream(out), personBuilder)

            assertEquals("name", personBuilder.build().name)
            assertEquals(1, out.toString().split("\n").size)
        }
        test1()

        fun test2(){
            val inp = byteArrayInputStream("")
            val out = ByteArrayOutputStream()

            personBuilder.setDefault()

            assertFailsWith(EOFException::class){
                personReaderImpl.askName(Scanner(inp), PrintStream(out), personBuilder)
            }
        }
        test2()
    }

    @Test
    fun askBirthday() {
        val personBuilder: PersonBuilder by di.instance()
        val format = "HH:mm dd.MM.yyyy"
        val formatter = DateTimeFormatter.ofPattern(format)

        fun test1(){
            val inp = byteArrayInputStream("11:11 11.11.1111")
            val out = ByteArrayOutputStream()

            personBuilder.setDefault()
            personReaderImpl.askBirthday(Scanner(inp), PrintStream(out), personBuilder)

            assertEquals(LocalDateTime.parse("11:11 11.11.1111", formatter), personBuilder.build().birthday)
            assertEquals(2, out.toString().split("\n").size)
        }
        test1()

        fun test2(){
            val inp = byteArrayInputStream("30:12 01.10.1968\n01:01 01.01.1973")
            val out = ByteArrayOutputStream()

            personBuilder.setDefault()
            personReaderImpl.askBirthday(Scanner(inp), PrintStream(out), personBuilder)

            assertEquals(LocalDateTime.parse("01:01 01.01.1973", formatter), personBuilder.build().birthday)
            assertEquals(5, out.toString().split("\n").size)
        }
        test2()

        fun test3(){
            val inp = byteArrayInputStream("time time time")
            val out = ByteArrayOutputStream()

            personBuilder.setDefault()

            assertFailsWith(EOFException::class){
                personReaderImpl.askBirthday(Scanner(inp), PrintStream(out), personBuilder)
            }
        }
        test3()

        fun test4(){
            val inp = byteArrayInputStream("\n")
            val out = ByteArrayOutputStream()

            personBuilder.setDefault()
            personReaderImpl.askBirthday(Scanner(inp), PrintStream(out), personBuilder)

            assertNull(personBuilder.build().birthday)
            assertEquals(2, out.toString().split("\n").size)
        }
        test4()

        fun test5(){
            val inp = byteArrayInputStream("\ntime time time\n")
            val out = ByteArrayOutputStream()

            personBuilder.setDefault()
            personReaderImpl.askBirthday(Scanner(inp), PrintStream(out), personBuilder)

            assertNull(personBuilder.build().birthday)
            assertEquals(2, out.toString().split("\n").size)
        }
        test5()
    }

    @Test
    fun askPerson() {
        val inp = byteArrayInputStream("name\n\nJAPAN\n")
        val out = ByteArrayOutputStream()

        val person = personReaderImpl.askPerson(inp, out)

        assertEquals("name", person.name)
        assertNull(person.birthday)
        assertEquals(Country.JAPAN, person.nationality)
    }
}