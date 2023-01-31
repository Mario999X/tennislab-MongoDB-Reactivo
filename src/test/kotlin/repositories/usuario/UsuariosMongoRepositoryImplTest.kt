package repositories.usuario

import db.MongoDbManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.Perfil
import models.Usuario
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import utils.Cifrador
import utils.TransformIDs

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UsuariosMongoRepositoryImplTest {
    private val usuario = Usuario(
        id = TransformIDs.generateIdUsers("999"),
        name = "Data1",
        email = "Data2@Data3.com",
        password = Cifrador.codifyPassword("Data4"),
        perfil = Perfil.ADMIN
    )

    @InjectMockKs
    private lateinit var usuariosMongoRepository: UsuariosMongoRepositoryImpl

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Usuario>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Usuario>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() = runTest {
        usuariosMongoRepository.save(usuario)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = usuariosMongoRepository.findAll().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByID() = runTest {
        val find = usuariosMongoRepository.findAll().toList()
        val res = usuariosMongoRepository.findByID(find[0].id)

        assertAll(
            { assertEquals(usuario.email, res!!.email) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = usuariosMongoRepository.delete(usuario)
        assertFalse(res)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = usuariosMongoRepository.save(usuario)

        assertAll(
            { assertEquals(usuario.email, res.email) }
        )
    }
}