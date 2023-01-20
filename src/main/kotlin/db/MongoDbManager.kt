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

    private const val MONGO_TYPE = "mongodb://"
    private const val HOST = "localhost"
    private const val PORT = 27017
    private const val DATABASE = "test"
    private const val OPTIONS = "?retryWrites=true&w=majority"

    private const val MONGO_URI = "$MONGO_TYPE$HOST/$DATABASE"

    init {
        log.debug("Inicializando conexion a MongoDB")

        println("Inicializando conexion a MongoDB -> $MONGO_URI$OPTIONS")
        mongoClient =
            KMongo.createClient("$MONGO_URI$OPTIONS")
                .coroutine
        database = mongoClient.getDatabase("test")
    }
}