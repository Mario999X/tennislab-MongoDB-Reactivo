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
import models.maquina.Personalizadora
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import repositories.maquina.MaquinaPersonalizadoraRepository
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MaquinaPersonalizadoraControllerTest {
    private val personalizadora = Personalizadora(
        descripcion = "Toshiba ABC",
        fechaAdquisicion = LocalDate.now().toString(),
        numSerie = 540L,
        maniobrabilidad = true,
        balance = false,
        rigidez = false
    )

    @MockK
    private lateinit var personalizadoraRepository: MaquinaPersonalizadoraRepository

    @InjectMockKs
    private lateinit var personalizadoraController: MaquinaPersonalizadoraController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Personalizadora>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Personalizadora>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPersonalizadoras() = runTest {
        every { personalizadoraRepository.findAll() } returns flowOf(personalizadora)
        val res = personalizadoraController.getPersonalizadoras().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { personalizadoraRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createPersonalizadora() = runTest {
        coEvery { personalizadoraRepository.save(personalizadora) } returns personalizadora
        val res = personalizadoraController.createPersonalizadora(personalizadora)
        assertAll(
            { assertEquals(res.descripcion, personalizadora.descripcion) }
        )

        coVerify(exactly = 1) { personalizadoraRepository.save(personalizadora) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPersonalizadoraById() = runTest {
        coEvery { personalizadoraRepository.findByID(personalizadora.id) } returns personalizadora
        val res = personalizadoraController.getPersonalizadoraById(personalizadora.id)
        assertAll(
            { assertEquals(res!!.descripcion, personalizadora.descripcion) }
        )

        coVerify(exactly = 1) { personalizadoraRepository.findByID(personalizadora.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deletePersonalizadora() = runTest {
        coEvery { personalizadoraRepository.delete(personalizadora) } returns true

        val res = personalizadoraController.deletePersonalizadora(personalizadora)
        assertTrue(res)
        coVerify(exactly = 1) { personalizadoraRepository.delete(personalizadora) }
    }
}