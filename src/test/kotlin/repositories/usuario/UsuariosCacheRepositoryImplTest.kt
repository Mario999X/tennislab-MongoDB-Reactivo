package repositories.usuario

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
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
import org.litote.kmongo.newId
import utils.Cifrador

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UsuariosCacheRepositoryImplTest {
    private val usuario = Usuario(
        newId(),
        name = "Data1",
        email = "Data2@Data3.com",
        password = Cifrador.codifyPassword("Data4"),
        perfil = Perfil.CLIENTE
    )

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
            { assertEquals(1, res.size) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = usuariosCacheRepository.delete(usuario)
        assertTrue(res)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = usuariosCacheRepository.save(usuario)

        assertAll(
            { assertEquals(usuario.email, res.email) }
        )
    }

    /*    @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun findByID() = runTest {
            val find = usuariosCacheRepository.findAll().toList()
            val res = usuariosCacheRepository.findByID(find[0].id)

            org.junit.jupiter.api.assertAll(
                { assertEquals(usuario.email, res!!.email) }
            )
        }*/
}