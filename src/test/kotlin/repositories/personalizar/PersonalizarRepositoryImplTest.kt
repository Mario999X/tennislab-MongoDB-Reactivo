package repositories.personalizar

import db.MongoDbManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import models.Personalizar
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PersonalizarRepositoryImplTest {
    private val personalizar = Personalizar(
        informacionPersonalizacion = "Dato1"
    )

    @InjectMockKs
    private lateinit var personalizarRepository: PersonalizarRepositoryImpl

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeAll
    fun setUp() = runTest {
        MongoDbManager.database.getCollection<Personalizar>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterAll
    fun tearDown() = runTest {
        MongoDbManager.database.getCollection<Personalizar>().drop()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun beforeEach() = runTest {
        personalizarRepository.save(personalizar)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = personalizarRepository.findAll().toList()

        assertAll(
            { kotlin.test.assertEquals(1, res.size) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByID() = runTest {
        val find = personalizarRepository.findAll().toList()
        val res = personalizarRepository.findByID(find[0].id)

        assertAll(
            { assertEquals(personalizar.informacionPersonalizacion, res!!.informacionPersonalizacion) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = personalizarRepository.save(personalizar)

        assertAll(
            { assertEquals(personalizar.informacionPersonalizacion, res.informacionPersonalizacion) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = personalizarRepository.delete(personalizar)
        assertTrue(res)
    }
}