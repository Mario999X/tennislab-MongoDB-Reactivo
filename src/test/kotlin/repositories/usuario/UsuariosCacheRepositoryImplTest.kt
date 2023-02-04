package repositories.usuario

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.Perfil
import models.Usuario
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import utils.Cifrador
import utils.TransformIDs

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UsuariosCacheRepositoryImplTest {
    private val usuario = Usuario(
        id = TransformIDs.generateIdUsers("999"),
        name = "Data1",
        email = "Data2@Data3.com",
        password = Cifrador.codifyPassword("Data4"),
        perfil = Perfil.CLIENTE
    )

    @MockK
    private lateinit var usuarioRepository: UsuarioRepository

    @InjectMockKs
    private lateinit var usuariosCacheRepository: UsuariosCacheRepositoryImpl

    init {
        MockKAnnotations.init(this)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = usuariosCacheRepository.findAll().toList()

        assertAll(
            { assertEquals(0, res.size) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = usuariosCacheRepository.delete(usuario)
        assertFalse(res)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = usuariosCacheRepository.save(usuario)

        assertAll(
            { assertEquals(usuario.email, res.email) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByID() = runTest {
        val res = usuariosCacheRepository.findByID(usuario.id)

        assertAll(
            {
                if (res != null) {
                    assertEquals(usuario.id, res.id)
                }
            }
        )

    }
}