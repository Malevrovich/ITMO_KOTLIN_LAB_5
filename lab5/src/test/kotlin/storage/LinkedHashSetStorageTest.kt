package storage

import data.coordinates.CoordinatesBuilder
import data.coordinates.CoordinatesBuilderImpl
import data.movie.MovieBuilder
import data.movie.MovieBuilderImpl
import data.movie.MovieGenre
import data.person.PersonBuilder
import data.person.PersonBuilderImpl
import org.junit.jupiter.api.Test
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import kotlin.test.*

internal class LinkedHashSetStorageTest {

    private val di = DI{
        bindProvider<MovieBuilder> { MovieBuilderImpl(di) }
        bindProvider<PersonBuilder> { PersonBuilderImpl() }
        bindProvider<CoordinatesBuilder> { CoordinatesBuilderImpl() }
    }

    private lateinit var storage: Storage
    private val movieBuilder: MovieBuilder by di.instance()
    private val personBuilder: PersonBuilder by di.instance()

    @BeforeTest
    fun setup(){
        storage = LinkedHashSetStorage(di)
        movieBuilder.clear()
    }

    @Test
    fun addGetAll() {
        val el1 = movieBuilder.buildDefault(1)
        val el2 = movieBuilder.buildDefault(2)
        val el3 = movieBuilder.setDefault().setOscarsCount(30).setId(1).build()

        storage.add(el1)
        assertEquals(storage.getSize(), 1)

        storage.add(el2)
        assertEquals(storage.getSize(), 2)

        storage.add(el3)
        assertEquals(storage.getSize(), 2)

        assertNotEquals(storage.getAll().find { it == el1 }!!.oscarsCount, el3.oscarsCount)

        assertEquals(storage.getAll(), listOf(el1, el2))
    }

    @Test
    fun update() {
        val old = movieBuilder.buildDefault(1)
        val new = movieBuilder.setDefault().setOscarsCount(30).setId(1).build()

        storage.add(old)
        assertEquals(storage.getAll().elementAt(0).oscarsCount, 1)

        storage.update(1, new)

        assertEquals(storage.getSize(), 1)
        assertEquals(storage.getAll().elementAt(0).oscarsCount, 30)

        val old2 = movieBuilder.buildDefault(2)
        val new2 = movieBuilder.setDefault().setOscarsCount(15).setId(2).build()

        storage.add(old2)
        assertEquals(storage.getSize(), 2)

        storage.update(1, old)
        storage.update(2, new2)

        assertEquals(storage.getSize(), 2)
        assertEquals(storage.getAll().find { it.id == 1 }!!.oscarsCount, 1)
        assertEquals(storage.getAll().find { it.id == 2 }!!.oscarsCount, 15)
    }

    @Test
    fun remove() {
        val el1 = movieBuilder.buildDefault(1)
        val el2 = movieBuilder.buildDefault(2)
        val el3 = movieBuilder.buildDefault(3)
        val el4 = movieBuilder.buildDefault(4)

        storage.add(el1)
        storage.add(el2)
        storage.add(el3)
        storage.add(el4)

        assertEquals(storage.getSize(), 4)
        assertEquals(storage.getAll(), listOf(el1, el2, el3, el4))

        storage.remove(el2.id)

        assertEquals(storage.getSize(), 3)
        assertEquals(storage.getAll(), listOf(el1, el3, el4))

        storage.remove(el4.id)

        assertEquals(storage.getSize(), 2)
        assertEquals(storage.getAll(), listOf(el1, el3))
    }

    @Test
    fun clear() {
        val el1 = movieBuilder.buildDefault(1)
        val el2 = movieBuilder.buildDefault(2)
        val el3 = movieBuilder.buildDefault(3)

        storage.add(el1)
        storage.add(el2)
        storage.add(el3)

        assertEquals(storage.getSize(), 3)
        assertEquals(storage.getAll(), listOf(el1, el2, el3))

        storage.clear()

        assertEquals(storage.getSize(), 0)
        assertTrue(storage.getAll().isEmpty())
    }

    @Test
    fun addIfMin() {
        val el1 = movieBuilder.buildDefault(1)
        val el2 = movieBuilder.buildDefault(2)
        val el3 = movieBuilder.buildDefault(3)
        val el4 = movieBuilder.buildDefault(4)
        val el5 = movieBuilder.buildDefault(5)

        storage.add(el3)
        storage.add(el5)

        assertEquals(storage.getSize(), 2)
        assertEquals(storage.getAll(), listOf(el3, el5))

        val res1 = storage.addIfMin(el4)

        assertEquals(storage.getSize(), 2)
        assertEquals(storage.getAll(), listOf(el3, el5))
        assertFalse(res1)

        val res2 = storage.addIfMin(el1)

        assertEquals(storage.getSize(), 3)
        assertEquals(storage.getAll(), listOf(el3, el5, el1))
        assertTrue(res2)

        val res3 = storage.addIfMin(el2)

        assertEquals(storage.getSize(), 3)
        assertEquals(storage.getAll(), listOf(el3, el5, el1))
        assertFalse(res3)
    }

    @Test
    fun removeGreater() {
        val el1 = movieBuilder.buildDefault(1)
        val el2 = movieBuilder.buildDefault(2)
        val el3 = movieBuilder.buildDefault(3)
        val el4 = movieBuilder.buildDefault(4)
        val el5 = movieBuilder.buildDefault(5)
        val el6 = movieBuilder.buildDefault(6)

        storage.add(el1)
        storage.add(el2)
        storage.add(el3)
        storage.add(el4)
        storage.add(el5)
        storage.add(el6)

        val res1 = storage.removeGreater(el6)

        assertEquals(storage.getSize(), 6)
        assertEquals(storage.getAll(), listOf(el1, el2, el3, el4, el5, el6))
        assertEquals(res1, 0)

        val res2 = storage.removeGreater(el4)

        assertEquals(storage.getSize(), 4)
        assertEquals(storage.getAll(), listOf(el1, el2, el3, el4))
        assertEquals(res2, 2)

        storage.add(el6)

        val res3 = storage.removeGreater(el1)

        assertEquals(storage.getSize(), 1)
        assertEquals(storage.getAll(), listOf(el1))
        assertEquals(res3, 4)

        val res4 = storage.removeGreater(el3)

        assertEquals(storage.getSize(), 1)
        assertEquals(storage.getAll(), listOf(el1))
        assertEquals(res4, 0)
    }

    @Test
    fun sumOfLength() {
        val el1 = movieBuilder.setDefault().setLength(2).setId(1).build()
        val el2 = movieBuilder.setDefault().setLength(4).setId(2).build()
        val el3 = movieBuilder.buildDefault(3)

        storage.add(el1)
        storage.add(el2)

        assertEquals(storage.sumOfLength(), 6)

        storage.add(el3)

        assertEquals(storage.sumOfLength(), 7)
    }

    @Test
    fun getUniqueGenre() {
        val el1 = movieBuilder.setDefault().setGenre(MovieGenre.TRAGEDY).setId(1).build()
        val el2 = movieBuilder.setDefault().setGenre(MovieGenre.TRAGEDY).setId(2).build()
        val el3 = movieBuilder.setDefault().setGenre(MovieGenre.DRAMA).setId(3).build()
        val el4 = movieBuilder.setDefault().setGenre(MovieGenre.WESTERN).setId(4).build()

        storage.add(el1)

        assertTrue(storage.getUniqueGenre().contains(MovieGenre.TRAGEDY))
        assertFalse(storage.getUniqueGenre().contains(MovieGenre.WESTERN))
        assertFalse(storage.getUniqueGenre().contains(MovieGenre.DRAMA))

        storage.add(el2)

        assertTrue(storage.getUniqueGenre().contains(MovieGenre.TRAGEDY))
        assertFalse(storage.getUniqueGenre().contains(MovieGenre.WESTERN))
        assertFalse(storage.getUniqueGenre().contains(MovieGenre.DRAMA))

        storage.add(el4)

        assertTrue(storage.getUniqueGenre().contains(MovieGenre.TRAGEDY))
        assertTrue(storage.getUniqueGenre().contains(MovieGenre.WESTERN))
        assertFalse(storage.getUniqueGenre().contains(MovieGenre.DRAMA))

        storage.add(el3)

        assertTrue(storage.getUniqueGenre().contains(MovieGenre.TRAGEDY))
        assertTrue(storage.getUniqueGenre().contains(MovieGenre.WESTERN))
        assertTrue(storage.getUniqueGenre().contains(MovieGenre.DRAMA))
    }

    @Test
    fun getDescendingScreenwriters() {
        val el1 = movieBuilder.setDefault().setScreenwriter(
            personBuilder.setName("a").build()
        ).build()

        val el2 = movieBuilder.setDefault().setScreenwriter(
            personBuilder.setName("b").build()
        ).build()

        val el3 = movieBuilder.setDefault().setScreenwriter(
            personBuilder.setName("c").build()
        ).build()

        storage.add(el1)
        storage.add(el3)

        assertEquals(storage.getDescendingScreenwriters(), listOf(el3, el1).map { it.screenwriter })

        storage.add(el2)

        assertEquals(storage.getDescendingScreenwriters(), listOf(el3, el2, el1).map { it.screenwriter })
    }

    @Test
    fun isEmpty(){
        assert(storage.isEmpty())

        storage.add(movieBuilder.buildDefault(1))
        storage.clear()

        assert(storage.isEmpty())
    }
}