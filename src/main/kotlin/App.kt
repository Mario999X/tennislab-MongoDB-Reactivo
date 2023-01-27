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
        val encordarController: EncordarController by inject()
        val personalizarController: PersonalizarController by inject()

        //Listas
        val productosList = mutableListOf<Producto>()
        val adquisicionList = mutableListOf<Adquisicion>()
        val encordadosList = mutableListOf<Encordar>()
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

            //Encordados
            val encordacionesInit = getEncordaciones()
            encordacionesInit.forEach { encordacion ->
                encordarController.createEncordacion(encordacion)
            }
            encordadosList.clear()
            encordarController.getEncordaciones().collect { encordacion ->
                encordadosList.add(encordacion)
            }
            encordadosList.forEach { encordacion ->
                println(encordacion)
            }

            //Personalizaciones
            val personalizacionesInit = getPersonalizaciones()
            personalizacionesInit.forEach { personalizar ->
                personalizarController.createPersonalizacion(personalizar)
            }
            personalizacionesList.clear()
            personalizarController.getPersonalizaciones().collect { personalizar ->
                personalizacionesList.add(personalizar)
            }
            personalizacionesList.forEach { personalizar ->
                println(personalizar)
            }

        }
        init.join()

        delay(1000)

        val update = launch {
            //Productos
            //GetById
            val producto = productoController.getProductoById(productosList[1].id)
            producto?.let { println(it) }
            //Update
            producto?.let {
                it.precio += 3.00
                productoController.updateProducto(it)
            }
            //Delete
            val productoDelete = productoController.getProductoById(productosList[0].id)
            if (productoDelete != null) {
                productoController.deleteProducto(productoDelete)
            }

            //Adquisición
            //GetById
            val adquisicion = adquisicionController.getAdquisicionById(adquisicionList[1].id)
            adquisicion?.let { println(it) }
            //Update
            adquisicion?.let {
                it.cantidad += 1
                adquisicionController.updateAdquisicion(it)
            }
            //Delete
            val adquisicionDelete = adquisicionController.getAdquisicionById(adquisicionList[0].id)
            if (adquisicionDelete != null) {
                adquisicionController.deleteAdquisicion(adquisicionDelete)
            }

            //Encordados
            //GetById
            val encordado = encordarController.getEncordacionById(encordadosList[1].id)
            encordado?.let { println(it) }
            //Update
            encordado?.let {
                it.informacionEndordado = "Cuerdas de plástico"
                encordarController.updateEncordacion(it)
            }
            //Delete
            val encordadoDelete = encordarController.getEncordacionById(encordadosList[0].id)
            if (encordadoDelete != null) {
                encordarController.deleteEncordacion(encordadoDelete)
            }

            //Personalizar
            //GetById
            val personalizacion = personalizarController.getPersonalizacionById(personalizacionesList[1].id)
            personalizacion?.let { println(it) }
            //Update
            personalizacion?.let {
                it.informacionPersonalizacion = "Reducción de peso"
                personalizarController.updatePersonalizacion(it)
            }
            //Delete
            val personalizacionDelete = personalizarController.getPersonalizacionById(personalizacionesList[0].id)
            if (personalizacionDelete != null) {
                personalizarController.deletePersonalizacion(personalizacionDelete)
            }

        }

        update.join()

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

