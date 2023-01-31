package repositories.maquina

import db.MongoDbManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.maquina.Personalizadora
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MaquinaPersonalizadoraRepositoryImplTest {
    private val personalizadora = Personalizadora(
        descripcion = "Toshiba ABC",
        fechaAdquisicion = LocalDate.now().toString(),
        numSerie = 540L,
        maniobrabilidad = true,
        balance = false,
        rigidez = false
    )

    @InjectMockKs
    private lateinit var personalizadoraRepository: MaquinaPersonalizadoraRepositoryImpl

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
    @BeforeEach
    fun beforeEach() = runTest {
        personalizadoraRepository.save(personalizadora)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = personalizadoraRepository.findAll().toList()

        assertAll(
            { kotlin.test.assertEquals(1, res.size) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByID() = runTest {
        val find = personalizadoraRepository.findAll().toList()
        val res = personalizadoraRepository.findByID(find[0].id)

        assertAll(
            { assertEquals(personalizadora.descripcion, res!!.descripcion) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = personalizadoraRepository.save(personalizadora)

        assertAll(
            { assertEquals(personalizadora.descripcion, res.descripcion) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = personalizadoraRepository.delete(personalizadora)
        assertTrue(res)
    }
}