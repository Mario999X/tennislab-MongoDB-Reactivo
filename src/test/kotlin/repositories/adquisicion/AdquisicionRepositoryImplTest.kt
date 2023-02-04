package repositories.adquisicion

import db.MongoDbManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.Adquisicion
import models.Producto
import models.Tipo
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.litote.kmongo.newId
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AdquisicionRepositoryImplTest {
    private val adquisicion = Adquisicion(
        cantidad = 1,
        producto = Producto(
            tipo = Tipo.RAQUETA,
            descripcion = "Babolat Pure Air",
            stock = 3,
            precio = 345.95
        ),
    )

    @InjectMockKs
    private lateinit var adquisicionRepository: AdquisicionRepositoryImpl

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Adquisicion>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Adquisicion>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() = runTest {
        adquisicionRepository.save(adquisicion)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = adquisicionRepository.findAll().toList()

        assertAll(
            { assertEquals(1, res.size) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByID() = runTest {
        val find = adquisicionRepository.findAll().toList()
        val res = adquisicionRepository.findByID(find[0].id)

        assertAll(
            { assertEquals(adquisicion.precio, res!!.precio) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        val res = adquisicionRepository.findByID(newId())

        assertNull(res)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = adquisicionRepository.save(adquisicion)

        assertAll(
            { assertEquals(adquisicion.precio, res.precio) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = adquisicionRepository.delete(adquisicion)
        assertTrue(res)
    }
}