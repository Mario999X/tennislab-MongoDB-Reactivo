import controllers.AdquisicionController
import controllers.EncordarController
import controllers.PersonalizarController
import controllers.ProductoController
import db.*
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.collect
import models.Adquisicion
import models.Encordar
import models.Personalizar
import models.Producto
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private val logger = KotlinLogging.logger { }

class AppMongo : KoinComponent {

    fun run(): Unit = runBlocking {
        val limpiar = launch {
            limpiarDatos()
        }
        limpiar.join()

        //Controladores
        val productoController: ProductoController by inject()
        val adquisicionController: AdquisicionController by inject()
        val encordacionesController: EncordarController by inject()
        val personalizacionesController: PersonalizarController by inject()

        //Listas
        val productosList = mutableListOf<Producto>()
        val adquisicionList = mutableListOf<Adquisicion>()
        val encordacionesList = mutableListOf<Encordar>()
        val personalizacionesList = mutableListOf<Personalizar>()

        //Escuchadores
        val escuchadorProducto = launch {
            println("Escuchando cambios en producto")
            productoController.watchProducto().collect {
                println("Evento: ${it.operationType.value} -> ${it.fullDocument}")
            }
        }

        //Iniciando datos
        val init = launch {
            //Productos
            val productosInit = getProductoInit()
            productosInit.forEach { producto ->
                productoController.createProducto(producto)
            }
            productosList.clear()
            productoController.getProductos().collect { producto ->
                productosList.add(producto)
            }
            productosList.forEach { producto ->
                println(producto)
            }

            //Adquisiciones
            val adquisicionesInit = getAdquisicionInit()
            adquisicionesInit.forEach { adquisicion ->
                adquisicionController.createAdquisicion(adquisicion)
            }
            adquisicionList.clear()
            adquisicionController.getAdquisiciones().collect { adquisicion ->
                adquisicionList.add(adquisicion)
            }
            adquisicionList.forEach { adquisicion ->
                println(adquisicion)
            }

            //Encordaciones
            val encordacionesInit = getEncordaciones()
            encordacionesInit.forEach { encordacion ->
                encordacionesController.createEncordacion(encordacion)
            }
            encordacionesList.clear()
            encordacionesController.getEncordaciones().collect { encordacion ->
                encordacionesList.add(encordacion)
            }
            encordacionesList.forEach { encordacion ->
                println(encordacion)
            }

            //Personalizaciones
            val personalizacionesInit = getPersonalizaciones()
            personalizacionesInit.forEach { personalizar ->
                personalizacionesController.createPersonalizacion(personalizar)
            }
            personalizacionesList.clear()
            personalizacionesController.getPersonalizaciones().collect { personalizar ->
                personalizacionesList.add(personalizar)
            }
            personalizacionesList.forEach { personalizar ->
                println(personalizar)
            }

        }
        init.join()

        delay(1000)

        //Terminando los escuchadores
        escuchadorProducto.cancel()
    }

    suspend fun limpiarDatos() = withContext(Dispatchers.IO) {
        logger.debug { "Borrando datos de la base de datos" }
        MongoDbManager.database.getCollection<Producto>().drop()
        MongoDbManager.database.getCollection<Adquisicion>().drop()
        MongoDbManager.database.getCollection<Encordar>().drop()
        MongoDbManager.database.getCollection<Personalizar>().drop()
    }
}

