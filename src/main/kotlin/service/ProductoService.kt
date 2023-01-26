package service

import com.mongodb.reactivestreams.client.ChangeStreamPublisher
import db.MongoDbManager
import models.Producto
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private val logger = KotlinLogging.logger { }

@Single
@Named("ProductoService")
class ProductoService {
    fun watch(): ChangeStreamPublisher<Producto> {
        logger.debug { "Watch()" }
        return MongoDbManager.database.getCollection<Producto>().watch<Producto>().publisher
    }
}