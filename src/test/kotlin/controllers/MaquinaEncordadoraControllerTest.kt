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
import models.maquina.Encordadora
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.newId
import repositories.maquina.MaquinaEncordadoraRepository
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MaquinaEncordadoraControllerTest {
    private val encordadora = Encordadora(
        descripcion = "Toshiba ABC",
        fechaAdquisicion = LocalDate.now().toString(),
        numSerie = 120L,
        isManual = true,
        tensionMax = 23.2,
        tensionMin = 20.5
    )

    @MockK
    private lateinit var encordadoraRepository: MaquinaEncordadoraRepository

    @InjectMockKs
    private lateinit var encordadoraController: MaquinaEncordadoraController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Encordadora>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Encordadora>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEncordadoras() = runTest {
        every { encordadoraRepository.findAll() } returns flowOf(encordadora)
        val res = encordadoraController.getEncordadoras().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { encordadoraRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createEncordadora() = runTest {
        coEvery { encordadoraRepository.save(encordadora) } returns encordadora
        val res = encordadoraController.createEncordadora(encordadora)
        assertAll(
            { assertEquals(res.descripcion, encordadora.descripcion) }
        )

        coVerify(exactly = 1) { encordadoraRepository.save(encordadora) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEncordadoraById() = runTest {
        coEvery { encordadoraRepository.findByID(encordadora.id) } returns encordadora
        val res = encordadoraController.getEncordadoraById(encordadora.id)
        assertAll(
            { assertEquals(res!!.descripcion, encordadora.descripcion) }
        )

        coVerify(exactly = 1) { encordadoraRepository.findByID(encordadora.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        coEvery { encordadoraRepository.findByID(any()) } returns null
        val res = encordadoraController.getEncordadoraById(newId())

        assertNull(res)

        coVerify(exactly = 2) { encordadoraRepository.findByID(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteEncordadora() = runTest {
        coEvery { encordadoraRepository.delete(encordadora) } returns true

        val res = encordadoraController.deleteEncordadora(encordadora)
        assertTrue(res)
        coVerify(exactly = 1) { encordadoraRepository.delete(encordadora) }
    }
}