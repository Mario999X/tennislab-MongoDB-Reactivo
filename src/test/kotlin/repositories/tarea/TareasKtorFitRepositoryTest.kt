package repositories.tarea

import db.MongoDbManager
import db.getAdquisicionInit
import db.getPersonalizaciones
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.Perfil
import models.Tarea
import models.Usuario
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.newId
import utils.Cifrador

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TareasKtorFitRepositoryTest {
    private val tarea = Tarea(
        adquisicion = getAdquisicionInit()[1],
        personalizar = getPersonalizaciones()[1],
        usuario = Usuario(
            newId(),
            name = "Data1",
            email = "Data2@Data3.com",
            password = Cifrador.codifyPassword("Data4"),
            perfil = Perfil.ENCORDADOR
        )
    )

    @InjectMockKs
    private lateinit var tareasRepository: TareasKtorFitRepository

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Tarea>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Tarea>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() = runTest {
        tareasRepository.save(tarea)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun uploadTarea() = runTest {
        val res = tareasRepository.uploadTarea(tarea)

        assertAll(
            { assertEquals(tarea.precio, res.precio) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = tareasRepository.findAll().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByID() = runTest {
        val find = tareasRepository.findAll().toList()
        val res = tareasRepository.findByID(find[0].id)

        assertAll(
            { assertEquals(tarea.precio, res!!.precio) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        val res = tareasRepository.findByID(newId())

        assertNull(res)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = tareasRepository.save(tarea)

        assertAll(
            { assertEquals(tarea.precio, res.precio) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = tareasRepository.delete(tarea)
        assertTrue(res)
    }
}