package repositories.encordar

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Encordar
import mu.KotlinLogging
import org.litote.kmongo.Id

private val logger = KotlinLogging.logger { }

/**
 * Repositorio de "Encordar", realiza operaciones CRUD básicas
 *@property EncordarRepository
 */
class EncordarRepositoryImpl : EncordarRepository {

    override fun findAll(): Flow<Encordar> {
        logger.debug { "findAll()" }
        return MongoDbManager.database.getCollection<Encordar>().find().publisher.asFlow()

    }

    override suspend fun findByID(id: Id<Encordar>): Encordar? {
        logger.debug { "findByID($id)" }
        return MongoDbManager.database.getCollection<Encordar>().findOneById(id)

    }

    override suspend fun save(entity: Encordar): Encordar {
        logger.debug { "save($entity)" }
        return MongoDbManager.database.getCollection<Encordar>().save(entity).let { entity }

    }

    override suspend fun delete(entity: Encordar): Boolean {
        logger.debug { "delete($entity)" }
        return MongoDbManager.database.getCollection<Encordar>().deleteOneById(entity.id).let { true }
    }
}