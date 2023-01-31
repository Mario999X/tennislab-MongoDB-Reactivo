package controllers

import db.MongoDbManager
import db.getAdquisicionInit
import db.getPersonalizaciones
import dto.toUsuarioDto
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.Perfil
import models.Tarea
import models.Usuario
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.newId
import repositories.tarea.TareasKtorFitRepository
import repositories.usuario.UsuariosCacheRepositoryImpl
import repositories.usuario.UsuariosKtorFitRepositoryImpl
import repositories.usuario.UsuariosMongoRepositoryImpl
import utils.Cifrador

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class APIControllerTest {
    private val usuario = Usuario(
        name = "Data1",
        email = "Data2@Data3.com",
        password = Cifrador.codifyPassword("Data4"),
        perfil = Perfil.ADMIN
    )

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

    @MockK
    private lateinit var usuariosCacheRepository: UsuariosCacheRepositoryImpl

    @MockK
    private lateinit var usuariosMongoRepository: UsuariosMongoRepositoryImpl

    @MockK
    private lateinit var usuariosKtorFitRepository: UsuariosKtorFitRepositoryImpl

    @MockK
    private lateinit var tareasKtorFitRepository: TareasKtorFitRepository

    @InjectMockKs
    private lateinit var apiController: APIController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Usuario>().drop()
        MongoDbManager.database.getCollection<Tarea>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Usuario>().drop()
        MongoDbManager.database.getCollection<Tarea>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() = runTest {
        MongoDbManager.database.getCollection<Usuario>().drop()
        MongoDbManager.database.getCollection<Tarea>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllUsuariosApi() = runTest {
        coEvery { usuariosKtorFitRepository.findAll() } returns flowOf(usuario.toUsuarioDto())
        val res = apiController.getAllUsuariosApi().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
        coVerify(exactly = 1) { usuariosKtorFitRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllUsuariosMongo() = runTest {
        coEvery { usuariosMongoRepository.findAll() } returns flowOf(usuario)
        val res = apiController.getAllUsuariosMongo().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
        coVerify(exactly = 1) { usuariosMongoRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllUsuariosCache() = runTest {
        coEvery { usuariosCacheRepository.findAll() } returns flowOf(usuario)
        val res = apiController.getAllUsuariosCache().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
        coVerify(exactly = 1) { usuariosCacheRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveUsuario() = runTest {
        coEvery { usuariosMongoRepository.save(usuario) } returns usuario
        coEvery { usuariosCacheRepository.save(usuario) } returns usuario

        val res = apiController.saveUsuario(usuario)

        assertAll(
            { assertEquals(res.name, usuario.name) }
        )

        coVerify(exactly = 1) { usuariosMongoRepository.save(usuario) }
        coVerify(exactly = 1) { usuariosCacheRepository.save(usuario) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getUsuarioById() = runTest {
        coEvery { usuariosCacheRepository.findByID(any()) } returns usuario

        val res = apiController.getUsuarioById(usuario.id)

        assertAll(
            { assertEquals(res!!.name, usuario.name) }
        )
        coVerify(exactly = 1) { usuariosCacheRepository.findByID(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteUsuario() = runTest {
        coEvery { usuariosMongoRepository.delete(usuario) } returns true
        coEvery { usuariosCacheRepository.delete(usuario) } returns true

        apiController.deleteUsuario(usuario)

        coVerify(exactly = 1) { usuariosMongoRepository.delete(usuario) }
        coVerify(exactly = 1) { usuariosCacheRepository.delete(usuario) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllTareas() = runTest {
        coEvery { tareasKtorFitRepository.findAll() } returns flowOf(tarea)
        val res = apiController.getAllTareas().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
        coVerify(exactly = 1) { tareasKtorFitRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveTarea() = runTest {
        coEvery { tareasKtorFitRepository.save(tarea) } returns tarea
        coEvery { tareasKtorFitRepository.uploadTarea(tarea) } returns tarea
        val res = apiController.saveTarea(tarea)

        assertAll(
            { assertEquals(res.precio, tarea.precio) }
        )
        coVerify { tareasKtorFitRepository.save(tarea) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTareaById() = runTest {
        coEvery { tareasKtorFitRepository.findByID(tarea.id) } returns tarea
        val res = apiController.getTareaById(tarea.id)

        assertAll(
            { assertEquals(res!!.precio, tarea.precio) }
        )
        coVerify { tareasKtorFitRepository.findByID(tarea.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteTarea() = runTest {
        coEvery { tareasKtorFitRepository.delete(tarea) } returns true
        val res = apiController.deleteTarea(tarea)

        assertTrue(res)
        coVerify { tareasKtorFitRepository.delete(tarea) }
    }
}