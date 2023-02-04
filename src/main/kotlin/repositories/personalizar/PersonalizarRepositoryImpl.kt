package repositories.personalizar

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Personalizar
import mu.KotlinLogging
import org.litote.kmongo.Id


private val logger = KotlinLogging.logger { }

/**
 * Repositorio de "Personalizar", realiza operaciones CRUD básicas
 *@property PersonalizarRepository
 */
class PersonalizarRepositoryImpl : PersonalizarRepository {

    override fun findAll(): Flow<Personalizar> {
        logger.debug { "findAll()" }
        return MongoDbManager.database.getCollection<Personalizar>().find().publisher.asFlow()
    }

    override suspend fun findByID(id: Id<Personalizar>): Personalizar? {
        logger.debug { "findByID($id)" }
        return MongoDbManager.database.getCollection<Personalizar>().findOneById(id)
    }

    override suspend fun save(entity: Personalizar): Personalizar {
        logger.debug { "save($entity)" }
        return MongoDbManager.database.getCollection<Personalizar>().save(entity).let { entity }
    }

    override suspend fun delete(entity: Personalizar): Boolean {
        logger.debug { "delete($entity)" }
        return MongoDbManager.database.getCollection<Personalizar>().deleteOneById(entity.id).let { true }
    }
}