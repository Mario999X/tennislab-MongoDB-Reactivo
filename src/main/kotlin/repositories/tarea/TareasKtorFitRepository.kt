package repositories.tarea

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import db.MongoDbManager
import dto.toTareaDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withContext
import ktorfit.KtorFitClient
import models.Tarea
import mu.KotlinLogging
import org.litote.kmongo.Id

private val logger = KotlinLogging.logger { }

/**
 * Repositorio de "Tareas", realiza operaciones CRUD básicas
 *@property TareaRepository
 */
class TareasKtorFitRepository : TareaRepository {

    private val client by lazy { KtorFitClient.instance }

    suspend fun uploadTarea(entity: Tarea): Tarea = withContext(Dispatchers.IO) {
        println("\tuploadAdquisicion")

        client.createTarea(entity.toTareaDto())
        return@withContext entity
    }

    override fun findAll(): Flow<Tarea> {
        logger.debug { "findAll()" }
        return MongoDbManager.database.getCollection<Tarea>().find().publisher.asFlow()
    }

    override suspend fun findByID(id: Id<Tarea>): Tarea? {
        logger.debug { "findByID($id)" }
        return MongoDbManager.database.getCollection<Tarea>().findOneById(id)
    }

    override suspend fun save(entity: Tarea): Tarea {
        logger.debug { "save($entity)" }
        return MongoDbManager.database.getCollection<Tarea>().save(entity).let { entity }
    }

    override suspend fun delete(entity: Tarea): Boolean {
        logger.debug { "delete($entity)" }
        return MongoDbManager.database.getCollection<Tarea>().deleteOneById(entity.id).let { true }
    }
}