package repositories.producto

import db.MongoDbManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import models.Producto
import models.Tipo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.newId

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProductosRepositoryImplTest {
    private val producto = Producto(
        tipo = Tipo.COMPLEMENTO,
        descripcion = "Wilson Dazzle",
        stock = 5,
        precio = 7.90
    )

    @InjectMockKs
    private lateinit var productosRepository: ProductosRepositoryImpl

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
    @BeforeEach
    fun beforeEach() = runTest {
        productosRepository.save(producto)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Order(1)
    fun findAll() = runTest {
        val result = productosRepository.findAll().toList()

        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(producto.descripcion, result[0].descripcion) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Order(2)
    fun findByID() = runTest {
        val find = productosRepository.findAll().toList()
        val result = productosRepository.findByID(find[0].id)
        assertAll(
            { assertEquals(producto.descripcion, result!!.descripcion) },
            { assertEquals(producto.stock, result!!.stock) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Order(3)
    fun findByIdNotFound() = runTest {
        val result = productosRepository.findByID(newId())
        assertNull(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Order(4)
    fun save() = runTest {
        val result = productosRepository.save(producto)

        assertAll(
            { assertEquals(producto.descripcion, result.descripcion) },
            { assertEquals(producto.stock, result.stock) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Order(5)
    fun delete() = runTest {
        val result = productosRepository.delete(producto)
        assertTrue(result)
    }
}