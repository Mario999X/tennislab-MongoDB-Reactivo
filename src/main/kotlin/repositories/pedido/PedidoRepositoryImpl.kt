package repositories.pedido

import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Pedido
import mu.KotlinLogging
import org.litote.kmongo.Id

/**
 * @author Mario Resa y Sebastián Mendoza
 */

private val logger = KotlinLogging.logger { }

/**
 * Repositorio de "Pedidos", realiza operaciones CRUD basicas
 *
 */
class PedidoRepositoryImpl : PedidoRepository {

    override fun findAll(): Flow<Pedido> {
        logger.debug { "findAll()" }
        return MongoDbManager.database.getCollection<Pedido>().find().publisher.asFlow()
    }

    override suspend fun findByID(id: Id<Pedido>): Pedido? {
        logger.debug { "findByID($id)" }
        return MongoDbManager.database.getCollection<Pedido>().findOneById(id)
    }

    override suspend fun save(entity: Pedido): Pedido {
        logger.debug { "save($entity)" }
        return MongoDbManager.database.getCollection<Pedido>().save(entity).let { entity }
    }

    override suspend fun delete(entity: Pedido): Boolean {
        logger.debug { "delete($entity)" }
        return MongoDbManager.database.getCollection<Pedido>().deleteOneById(entity.id).let { true }
    }
}