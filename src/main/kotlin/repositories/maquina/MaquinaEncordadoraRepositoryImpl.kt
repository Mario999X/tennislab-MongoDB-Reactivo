package repositories.maquina

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.maquina.Encordadora
import models.maquina.Maquina
import mu.KotlinLogging
import org.litote.kmongo.Id

private val logger = KotlinLogging.logger { }

/**
 * Repositorio de "Encordadora", realiza operaciones CRUD básicas
 *@property MaquinaEncordadoraRepository
 */
class MaquinaEncordadoraRepositoryImpl : MaquinaEncordadoraRepository {
    override fun findAll(): Flow<Encordadora> {
        logger.debug { "findAll()" }
        return MongoDbManager.database.getCollection<Encordadora>().find().publisher.asFlow()
    }

    override suspend fun findByID(id: Id<Maquina>): Encordadora? {
        logger.debug { "findByID($id)" }
        return MongoDbManager.database.getCollection<Encordadora>().findOneById(id)
    }

    override suspend fun save(entity: Encordadora): Encordadora {
        logger.debug { "save($entity)" }
        return MongoDbManager.database.getCollection<Encordadora>().save(entity).let { entity }
    }

    override suspend fun delete(entity: Encordadora): Boolean {
        logger.debug { "delete($entity)" }
        return MongoDbManager.database.getCollection<Encordadora>().deleteOneById(entity.id).let { true }
    }
}