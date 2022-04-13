package data

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import server.data.IdGenerator

internal class IdGeneratorTest {

    private val di = DI{
        bindSingleton { IdGenerator() }
    }

    private val idGenerator: IdGenerator by di.instance()

    @Test
    fun generate() {
        assertEquals(idGenerator.generate(), 1)
        assertEquals(idGenerator.generate(), 2)
        assertEquals(idGenerator.generate(), 3)
    }
}