package repositories.maquina

import db.MongoDbManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.maquina.Encordadora
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MaquinaEncordadoraRepositoryImplTest {
    private val encordadora = Encordadora(
        descripcion = "Toshiba ABC",
        fechaAdquisicion = LocalDate.now().toString(),
        numSerie = 120L,
        isManual = true,
        tensionMax = 23.2,
        tensionMin = 20.5
    )

    @InjectMockKs
    private lateinit var encordadoraRepository: MaquinaEncordadoraRepositoryImpl

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
    @BeforeEach
    fun beforeEach() = runTest {
        encordadoraRepository.save(encordadora)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = encordadoraRepository.findAll().toList()

        assertAll(
            { kotlin.test.assertEquals(1, res.size) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByID() = runTest {
        val find = encordadoraRepository.findAll().toList()
        val res = encordadoraRepository.findByID(find[0].id)

        assertAll(
            { assertEquals(encordadora.isManual, res!!.isManual) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = encordadoraRepository.save(encordadora)

        assertAll(
            { assertEquals(encordadora.isManual, res.isManual) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = encordadoraRepository.delete(encordadora)
        kotlin.test.assertTrue(res)
    }
}