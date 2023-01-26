package service

import com.mongodb.reactivestreams.client.ChangeStreamPublisher
import db.MongoDbManager
import models.Adquisicion
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private val logger = KotlinLogging.logger { }

@Single
@Named("AdquisicionService")
class AdquisicionService {
    fun watch(): ChangeStreamPublisher<Adquisicion> {
        logger.debug { "Watch()" }
        return MongoDbManager.database.getCollection<Adquisicion>().watch<Adquisicion>().publisher
    }
}