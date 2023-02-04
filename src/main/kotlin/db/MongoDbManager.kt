package db

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import mu.KotlinLogging
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import utils.Propiedades

private val log = KotlinLogging.logger { }

/**
 * Clase Object que maneja la conexión a MongoDB, siendo en este caso a Mongo Atlas
 */
object MongoDbManager {

    private val properties = Propiedades.propertiesReader("config.properties")
    private var mongoClient: CoroutineClient
    var database: CoroutineDatabase

    private val MONGO_TYPE = properties.getProperty("mongo.type")
    private val HOST = properties.getProperty("host")
    private val DATABASE = properties.getProperty("database")
    private const val USERNAME = "tennisLab"
    private const val PASSWORD = "mongoreactivo"
    private val OPTIONS = properties.getProperty("options")

    private val MONGO_URI =
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