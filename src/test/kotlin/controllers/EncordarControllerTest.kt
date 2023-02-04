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
import models.Encordar
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.newId
import repositories.encordar.EncordarRepository

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EncordarControllerTest {
    private val encordado = Encordar(
        informacionEndordado = "Dato1"
    )

    @MockK
    private lateinit var encordarRepository: EncordarRepository

    @InjectMockKs
    private lateinit var encordarController: EncordarController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Encordar>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Encordar>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEncordados() = runTest {
        every { encordarRepository.findAll() } returns flowOf(encordado)
        val res = encordarController.getEncordados().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { encordarRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createEncordados() = runTest {
        coEvery { encordarRepository.save(encordado) } returns encordado
        val res = encordarController.createEncordado(encordado)
        assertAll(
            { assertEquals(res.informacionEndordado, encordado.informacionEndordado) }
        )

        coVerify(exactly = 1) { encordarRepository.save(encordado) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getEncordadoById() = runTest {
        coEvery { encordarRepository.findByID(encordado.id) } returns encordado
        val res = encordarController.getEncordadoById(encordado.id)
        assertAll(
            { assertEquals(res!!.informacionEndordado, encordado.informacionEndordado) }
        )

        coVerify(exactly = 1) { encordarRepository.findByID(encordado.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        coEvery { encordarRepository.findByID(any()) } returns null
        val res = encordarController.getEncordadoById(newId())

        assertNull(res)

        coVerify(exactly = 2) { encordarRepository.findByID(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteEncordado() = runTest {
        coEvery { encordarRepository.delete(encordado) } returns true

        val res = encordarController.deleteEncordado(encordado)
        assertTrue(res)
        coVerify(exactly = 1) { encordarRepository.delete(encordado) }
    }
}