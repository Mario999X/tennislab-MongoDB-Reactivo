package repositories.pedido

import db.MongoDbManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.*
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.newId
import utils.Cifrador
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PedidoRepositoryImplTest {
    private val pedido = Pedido(
        estadoPedido = EstadoPedido.PROCESANDO,
        fechaEntrada = LocalDateTime.now().toString(),
        fechaProgramada = LocalDateTime.now().plusDays(10).toString(),
        cliente = Usuario(
            newId(),
            name = "Data1",
            email = "Data2@Data3.com",
            password = Cifrador.codifyPassword("Data4"),
            perfil = Perfil.CLIENTE
        ),
        tareas = listOf()
    )

    @InjectMockKs
    private lateinit var pedidoRepository: PedidoRepositoryImpl

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Pedido>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Pedido>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() = runTest {
        pedidoRepository.save(pedido)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = pedidoRepository.findAll().toList()

        assertAll(
            { kotlin.test.assertEquals(1, res.size) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByID() = runTest {
        val find = pedidoRepository.findAll().toList()
        val res = pedidoRepository.findByID(find[0].id)

        assertAll(
            { assertEquals(pedido.estadoPedido, res!!.estadoPedido) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = pedidoRepository.save(pedido)

        assertAll(
            { assertEquals(pedido.estadoPedido, res.estadoPedido) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = pedidoRepository.delete(pedido)
        assertTrue(res)
    }
}