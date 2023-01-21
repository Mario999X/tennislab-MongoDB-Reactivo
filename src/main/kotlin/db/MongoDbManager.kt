package db

import mu.KotlinLogging
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

private val log = KotlinLogging.logger { }

object MongoDbManager {
    private var mongoClient: CoroutineClient
    lateinit var database: CoroutineDatabase

    private const val MONGO_TYPE = "mongodb+srv://"
    private const val HOST = "dam.ahgscx9.mongodb.net"
    private const val PORT = 27017
    private const val DATABASE = "tennisLab"
    private const val USERNAME = "tennisLab"
    private const val PASSWORD = "mongoreactivo"
    private const val OPTIONS = "?authSource=admin&retryWrites=true&w=majority"

    private const val MONGO_URI =
        "$MONGO_TYPE$USERNAME:$PASSWORD@$HOST/$DATABASE"

    init {
        log.debug("Inicializando conexión a MongoDB")

        println("Inicializando conexión a MongoDB -> $MONGO_URI$OPTIONS")
        mongoClient =
            KMongo.createClient("$MONGO_URI$OPTIONS")
                .coroutine
        database = mongoClient.getDatabase("tennisLab")
    }
}