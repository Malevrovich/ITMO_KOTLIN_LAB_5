package data.coordinates

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.EOFException
import java.io.PrintStream
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.assertFailsWith

internal class CoordinatesReaderImplTest {

    private val di = DI{
        bindProvider<CoordinatesBuilder> { CoordinatesBuilderImpl() }
    }

    lateinit var coordinatesReader: CoordinatesReader

    @BeforeTest
    fun setup(){
        coordinatesReader = CoordinatesReaderImpl(di)
    }

    private fun byteArrayInputStream(s: String): ByteArrayInputStream {
        return ByteArrayInputStream(s.toByteArray())
    }

    @Test
    fun askX() {
        val coordinatesBuilder: CoordinatesBuilder by di.instance()
        val delta = 0.001f

        fun test1(){
            val inp = byteArrayInputStream("123,321")
            val out = ByteArrayOutputStream()

            coordinatesBuilder.setDefault()
            coordinatesReader.askX(Scanner(inp), PrintStream(out), coordinatesBuilder)

            assertEquals(123.321f, coordinatesBuilder.build().x, delta)
            assertEquals(1, out.toString().split("\n").size)
        }
        test1()

        fun test2(){
            val inp = byteArrayInputStream("\n\n1,23")
            val out = ByteArrayOutputStream()

            coordinatesBuilder.setDefault()
            coordinatesReader.askX(Scanner(inp), PrintStream(out), coordinatesBuilder)

            assertEquals(1.23f, coordinatesBuilder.build().x, delta)
            assertEquals(2, out.toString().split("\n").size)
        }
        test2()

        fun test3(){
            val inp = byteArrayInputStream("abs")
            val out = ByteArrayOutputStream()

            coordinatesBuilder.setDefault()

            assertFailsWith(EOFException::class) {
                coordinatesReader.askX(Scanner(inp), PrintStream(out), coordinatesBuilder)
            }
        }
        test3()

        fun test4(){
            val inp = byteArrayInputStream("-1000\n-100,12")
            val out = ByteArrayOutputStream()

            coordinatesBuilder.setDefault()
            coordinatesReader.askX(Scanner(inp), PrintStream(out), coordinatesBuilder)

            assertEquals(-100.12f, coordinatesBuilder.build().x, delta)
            assertEquals(2, out.toString().split("\n").size)
        }
        test4()
    }

    @Test
    fun askY() {
        val coordinatesBuilder: CoordinatesBuilder by di.instance()

        fun test1(){
            val inp = byteArrayInputStream("-1000")
            val out = ByteArrayOutputStream()

            coordinatesBuilder.setDefault()
            coordinatesReader.askY(Scanner(inp), PrintStream(out), coordinatesBuilder)

            assertEquals(-1000, coordinatesBuilder.build().y)
            assertEquals(1, out.toString().split("\n").size)
        }
        test1()

        fun test2(){
            val inp = byteArrayInputStream("\n\n100")
            val out = ByteArrayOutputStream()

            coordinatesBuilder.setDefault()
            coordinatesReader.askY(Scanner(inp), PrintStream(out), coordinatesBuilder)

            assertEquals(100, coordinatesBuilder.build().y)
            assertEquals(2, out.toString().split("\n").size)
        }
        test2()

        fun test3(){
            val inp = byteArrayInputStream("abc\n10,32\n0")
            val out = ByteArrayOutputStream()

            coordinatesBuilder.setDefault()
            coordinatesReader.askY(Scanner(inp), PrintStream(out), coordinatesBuilder)

            assertEquals(0, coordinatesBuilder.build().y)
            assertEquals(3, out.toString().split("\n").size)
        }
        test3()

        fun test4(){
            val inp = byteArrayInputStream("")
            val out = ByteArrayOutputStream()

            coordinatesBuilder.setDefault()

            assertFailsWith(EOFException::class){
                coordinatesReader.askY(Scanner(inp), PrintStream(out), coordinatesBuilder)
            }
        }
        test4()
    }

    @Test
    fun askCoordinates() {
        val inp = byteArrayInputStream("100\n-100")
        val out = ByteArrayOutputStream()

        val coords = coordinatesReader.askCoordinates(inp, out)

        assertEquals(100f, coords.x, 0.001f)
        assertEquals(-100, coords.y)
    }
}