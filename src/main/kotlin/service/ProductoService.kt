package service

import com.mongodb.reactivestreams.client.ChangeStreamPublisher
import db.MongoDbManager
import models.Producto
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

class ProductoService {
    fun watch(): ChangeStreamPublisher<Producto> {
        logger.debug { "Watch()" }
        return MongoDbManager.database.getCollection<Producto>().watch<Producto>().publisher
    }
}