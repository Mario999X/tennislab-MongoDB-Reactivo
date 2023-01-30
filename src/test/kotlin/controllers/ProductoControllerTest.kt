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
import models.Producto
import models.Tipo
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.producto.ProductosRepository
import service.ProductoService

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProductoControllerTest {
    private val producto = Producto(
        tipo = Tipo.COMPLEMENTO,
        descripcion = "Wilson Dazzle",
        stock = 5,
        precio = 7.90
    )

    @MockK
    lateinit var productosRepository: ProductosRepository

    @MockK
    lateinit var productoService: ProductoService

    @InjectMockKs
    lateinit var productoController: ProductoController

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Producto>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Producto>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductos() = runTest {
        every { productosRepository.findAll() } returns flowOf(producto)
        val res = productoController.getProductos().toList()
        assertAll(
            { assertEquals(1, res.size) }
        )
        verify(exactly = 1) { productosRepository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createProducto() = runTest {
        coEvery { productosRepository.save(producto) } returns producto
        val res = productoController.createProducto(producto)
        assertAll(
            { assertEquals(res.descripcion, producto.descripcion) }
        )

        coVerify(exactly = 1) { productosRepository.save(producto) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductoById() = runTest {
        coEvery { productosRepository.findByID(producto.id) } returns producto
        val res = productoController.getProductoById(producto.id)
        assertAll(
            { assertEquals(res!!.descripcion, producto.descripcion) }
        )

        coVerify(exactly = 1) { productosRepository.findByID(producto.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateProducto() = runTest {
/*        coEvery { productosRepository.save(producto) } returns producto
        val pro = async { productoController.createProducto(producto) }

        val product = pro.await()

        val res = productoController.getProductoById(product.id)
        println("❤❤❤❤❤❤❤❤$res")

        res!!.precio += 1.0

        productoController.updateProducto(res!!)

        assertAll(
            { assertNotEquals(producto.precio, res.precio) }
        )

        coVerify(exactly = 1) { productosRepository.save(producto) }*/
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteProducto() = runTest {
        coEvery { productosRepository.delete(producto) } returns true

        val res = productoController.deleteProducto(producto)
        assertTrue(res)
        coVerify(exactly = 1) { productosRepository.delete(producto) }
    }
}