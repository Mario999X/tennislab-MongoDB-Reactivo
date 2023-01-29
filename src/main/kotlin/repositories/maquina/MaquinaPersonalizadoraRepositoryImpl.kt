package repositories.maquina

/**
 * @author Mario Resa y Sebatián Mendoza
 */
import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.maquina.Maquina
import models.maquina.Personalizadora
import mu.KotlinLogging
import org.litote.kmongo.Id

private val logger = KotlinLogging.logger { }

/**
 * Repositorio de "Personalizadora", realiza operaciones CRUD básicas
 *@property MaquinaPersonalizadoraRepository
 */
class MaquinaPersonalizadoraRepositoryImpl : MaquinaPersonalizadoraRepository {
    override fun findAll(): Flow<Personalizadora> {
        logger.debug { "findAll()" }
        return MongoDbManager.database.getCollection<Personalizadora>().find().publisher.asFlow()
    }

    override suspend fun findByID(id: Id<Maquina>): Personalizadora? {
        logger.debug { "findByID($id)" }
        return MongoDbManager.database.getCollection<Personalizadora>().findOneById(id)
    }

    override suspend fun save(entity: Personalizadora): Personalizadora {
        logger.debug { "save($entity)" }
        return MongoDbManager.database.getCollection<Personalizadora>().save(entity).let { entity }
    }

    override suspend fun delete(entity: Personalizadora): Boolean {
        logger.debug { "delete($entity)" }
        return MongoDbManager.database.getCollection<Personalizadora>().deleteOneById(entity.id).let { true }
    }
}