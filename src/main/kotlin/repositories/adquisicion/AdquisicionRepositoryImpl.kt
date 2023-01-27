package repositories.adquisicion

import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Adquisicion
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.litote.kmongo.Id

private val logger = KotlinLogging.logger { }

@Single
@Named("AdquisicionRepository")
class AdquisicionRepositoryImpl : AdquisicionRepository {
    override fun findAll(): Flow<Adquisicion> {
        logger.debug { "findAll()" }
        return MongoDbManager.database.getCollection<Adquisicion>().find().publisher.asFlow()
    }

    override suspend fun findByID(id: Id<Adquisicion>): Adquisicion? {
        logger.debug { "findByID($id)" }
        return MongoDbManager.database.getCollection<Adquisicion>().findOneById(id)
    }

    override suspend fun save(entity: Adquisicion): Adquisicion {
        logger.debug { "save($entity) - creando" }
        return MongoDbManager.database.getCollection<Adquisicion>().save(entity).let { entity }
    }

    override suspend fun delete(entity: Adquisicion): Boolean {
        logger.debug { "delete($entity)" }
        return MongoDbManager.database.getCollection<Adquisicion>().deleteOneById(entity.id).let { true }
    }
}