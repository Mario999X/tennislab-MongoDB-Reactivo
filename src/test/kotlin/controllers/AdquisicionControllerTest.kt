package controllers

import db.MongoDbManager
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.Adquisicion
import models.Producto
import models.Tipo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.newId
import repositories.adquisicion.AdquisicionRepository

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AdquisicionControllerTest {
    private val adquisicion = Adquisicion(
        cantidad = 1,
        producto = Producto(
            tipo = Tipo.RAQUETA,
            descripcion = "Babolat Pure Air",
            stock = 3,
            precio = 345.95
        ),
    )

    @MockK
    private lateinit var adquisicionRepository: AdquisicionRepository

    @InjectMockKs
    private lateinit var adquisicionController: AdquisicionController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Adquisicion>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Adquisicion>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAdquisiciones() = runTest {
        every { adquisicionRepository.findAll() } returns flowOf(adquisicion)
        val res = adquisicionController.getAdquisiciones().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { adquisicionRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createAdquisicion() = runTest {
        coEvery { adquisicionRepository.save(adquisicion) } returns adquisicion
        val res = adquisicionController.createAdquisicion(adquisicion)
        assertAll(
            { assertEquals(res.precio, adquisicion.precio) }
        )

        coVerify(exactly = 1) { adquisicionRepository.save(adquisicion) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAdquisicionById() = runTest {
        coEvery { adquisicionRepository.findByID(adquisicion.id) } returns adquisicion
        val res = adquisicionController.getAdquisicionById(adquisicion.id)
        assertAll(
            { assertEquals(res!!.precio, adquisicion.precio) }
        )

        coVerify(exactly = 1) { adquisicionRepository.findByID(adquisicion.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        coEvery { adquisicionRepository.findByID(any()) } returns null
        val res = adquisicionController.getAdquisicionById(newId())

        assertNull(res)

        coVerify(exactly = 1) { adquisicionRepository.findByID(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteAdquisicion() = runTest {
        coEvery { adquisicionRepository.delete(adquisicion) } returns true

        val res = adquisicionController.deleteAdquisicion(adquisicion)
        assertTrue(res)
        coVerify(exactly = 1) { adquisicionRepository.delete(adquisicion) }
    }
}
