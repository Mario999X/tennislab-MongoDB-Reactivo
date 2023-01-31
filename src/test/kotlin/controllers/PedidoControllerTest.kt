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
import models.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import repositories.pedido.PedidoRepository
import utils.Cifrador
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PedidoControllerTest {
    private val pedido = Pedido(
        estadoPedido = EstadoPedido.PROCESANDO,
        fechaEntrada = LocalDateTime.now().toString(),
        fechaProgramada = LocalDateTime.now().plusDays(10).toString(),
        cliente = Usuario(
            name = "Data1",
            email = "Data2@Data3.com",
            password = Cifrador.codifyPassword("Data4"),
            perfil = Perfil.CLIENTE
        ),
        tareas = listOf()
    )

    @MockK
    private lateinit var pedidoRepository: PedidoRepository

    @InjectMockKs
    private lateinit var pedidoController: PedidoController

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
    @Test
    fun getPedidos() = runTest {
        every { pedidoRepository.findAll() } returns flowOf(pedido)
        val res = pedidoController.getPedidos().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { pedidoRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createPedido() = runTest {
        coEvery { pedidoRepository.save(pedido) } returns pedido
        val res = pedidoController.createPedido(pedido)
        assertAll(
            { assertEquals(res.precio, pedido.precio) }
        )

        coVerify(exactly = 1) { pedidoRepository.save(pedido) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPedidoById() = runTest {
        coEvery { pedidoRepository.findByID(pedido.id) } returns pedido
        val res = pedidoController.getPedidoById(pedido.id)
        assertAll(
            { assertEquals(res!!.precio, pedido.precio) }
        )

        coVerify(exactly = 1) { pedidoRepository.findByID(pedido.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deletePedido() = runTest {
        coEvery { pedidoRepository.delete(pedido) } returns true

        val res = pedidoController.deletePedido(pedido)
        assertTrue(res)
        coVerify(exactly = 1) { pedidoRepository.delete(pedido) }
    }
}