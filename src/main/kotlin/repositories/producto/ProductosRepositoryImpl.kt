package repositories.producto

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import models.Producto
import mu.KotlinLogging
import org.litote.kmongo.Id

private val logger = KotlinLogging.logger { }

/**
 * Repositorio de "Productos", realiza operaciones CRUD basicas.
 *
 */
class ProductosRepositoryImpl : ProductosRepository {
    override fun findAll(): Flow<Producto> {
        logger.debug { "findAll()" }
        return MongoDbManager.database.getCollection<Producto>().find().publisher.asFlow()
    }

    override suspend fun findByID(id: Id<Producto>): Producto? {
        logger.debug { "findByID($id)" }
        return MongoDbManager.database.getCollection<Producto>().findOneById(id)
    }

    override suspend fun save(entity: Producto): Producto {
        logger.debug { "save($entity) - creando" }
        return MongoDbManager.database.getCollection<Producto>().save(entity).let { entity }
    }

    override suspend fun delete(entity: Producto): Boolean {
        logger.debug { "delete($entity)" }
        return MongoDbManager.database.getCollection<Producto>().deleteOneById(entity.id).let { true }
    }
}