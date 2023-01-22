import controllers.AdquisicionController
import controllers.ProductoController
import db.MongoDbManager
import db.getAdquisicionInit
import db.getProductoInit
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.collect
import models.Adquisicion
import models.Producto
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.producto.ProductosRepositoryImpl
import service.AdquisicionService
import service.ProductoService

private val logger = KotlinLogging.logger { }

class AppMongo : KoinComponent {

    fun run(): Unit = runBlocking {
        val limpiar = launch {
            limpiarDatos()
        }
        limpiar.join()

        //Controladores
        val productoController = ProductoController(ProductosRepositoryImpl(), ProductoService())
        val adquisicionController = AdquisicionController(AdquisicionRepositoryImpl(), AdquisicionService())

        //Listas
        val productosList = mutableListOf<Producto>()
        val adquisicionList = mutableListOf<Adquisicion>()

        //Escuchadores
        val escuchadorProducto = launch {
            println("Escuchando cambios en producto")
            productoController.watchProducto().collect {
                println("Evento: ${it.operationType.value} -> ${it.fullDocument}")
            }
        }
        val escuchadorAquisicion = launch {
            println("Escuchando cambios en adquisición")
            adquisicionController.watchAdquisicion().collect {
                println("Envento: ${it.operationType.value} -> ${it.fullDocument}")
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


        }
        init.join()

        delay(1000)

        //Terminando los escuchadores
        escuchadorAquisicion.cancel()
        escuchadorProducto.cancel()

    }

    suspend fun limpiarDatos() = withContext(Dispatchers.IO) {
        logger.debug { "Borrando datos de la base de datos" }
        MongoDbManager.database.getCollection<Producto>().drop()
        MongoDbManager.database.getCollection<Adquisicion>().drop()
    }
}

