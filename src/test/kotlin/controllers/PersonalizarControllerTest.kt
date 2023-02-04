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
import models.Personalizar
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.newId
import repositories.personalizar.PersonalizarRepository

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PersonalizarControllerTest {
    private val personalizar = Personalizar(
        informacionPersonalizacion = "Dato1"
    )

    @MockK
    private lateinit var personalizarRepository: PersonalizarRepository

    @InjectMockKs
    private lateinit var personalizarController: PersonalizarController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Personalizar>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Personalizar>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPersonalizaciones() = runTest {
        every { personalizarRepository.findAll() } returns flowOf(personalizar)
        val res = personalizarController.getPersonalizaciones().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { personalizarRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createPersonalizacion() = runTest {
        coEvery { personalizarRepository.save(personalizar) } returns personalizar
        val res = personalizarController.createPersonalizacion(personalizar)
        assertAll(
            { assertEquals(res.precio, personalizar.precio) }
        )

        coVerify(exactly = 1) { personalizarRepository.save(personalizar) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPersonalizacionById() = runTest {
        coEvery { personalizarRepository.findByID(personalizar.id) } returns personalizar
        val res = personalizarController.getPersonalizacionById(personalizar.id)
        assertAll(
            { assertEquals(res!!.precio, personalizar.precio) }
        )

        coVerify(exactly = 1) { personalizarRepository.findByID(personalizar.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        coEvery { personalizarRepository.findByID(any()) } returns null
        val res = personalizarController.getPersonalizacionById(newId())

        assertNull(res)

        coVerify(exactly = 2) { personalizarRepository.findByID(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deletePersonalizacion() = runTest {
        coEvery { personalizarRepository.delete(personalizar) } returns true

        val res = personalizarController.deletePersonalizacion(personalizar)
        assertTrue(res)
        coVerify(exactly = 1) { personalizarRepository.delete(personalizar) }
    }
}