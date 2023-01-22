package service

import com.mongodb.reactivestreams.client.ChangeStreamPublisher
import db.MongoDbManager
import models.Adquisicion
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

class AdquisicionService {
    fun watch(): ChangeStreamPublisher<Adquisicion> {
        logger.debug { "Watch()" }
        return MongoDbManager.database.getCollection<Adquisicion>().watch<Adquisicion>().publisher
    }
}