/**
 * @author Mario Resa y Sebastián Mendoza
 */
import controllers.*
import db.*
import kotlinx.coroutines.*
import kotlinx.coroutines.reactive.collect
import models.Adquisicion
import models.Encordar
import models.Personalizar
import models.Producto
import models.maquina.Encordadora
import models.maquina.Personalizadora
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private val logger = KotlinLogging.logger { }

/**
 * App donde se realiza parte del CRUD de la aplicación
 * @property KoinComponent
 */
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
        val maquinaEncordadoraController: MaquinaEncordadoraController by inject()
        val maquinaPersonalizadoraController: MaquinaPersonalizadoraController by inject()

        //Listas
        val productosList = mutableListOf<Producto>()
        val adquisicionList = mutableListOf<Adquisicion>()
        val encordadosList = mutableListOf<Encordar>()
        val personalizacionesList = mutableListOf<Personalizar>()
        val maquinaEncordadoraList = mutableListOf<Encordadora>()
        val maquinaPersonalizadoraList = mutableListOf<Personalizadora>()

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
                encordarController.createEncordados(encordacion)
            }
            encordadosList.clear()
            encordarController.getEncordados().collect { encordacion ->
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

            //Maquinas encordadoras
            val maquinasEncordadorasInit = getEncordadorasInit()
            maquinasEncordadorasInit.forEach { maquinas ->
                maquinaEncordadoraController.createEncordadora(maquinas)
            }
            maquinaEncordadoraList.clear()
            maquinaEncordadoraController.getEncordadoras().collect { encordadora ->
                maquinaEncordadoraList.add(encordadora)
            }
            maquinaEncordadoraList.forEach { maquinas ->
                println(maquinas)
            }

            //Maquinas personalizadoras
            val maquinaPersonalizadorasInit = getPersonalizadorasInit()
            maquinaPersonalizadorasInit.forEach { maquinas ->
                maquinaPersonalizadoraController.createPersonalizadora(maquinas)
            }
            maquinaPersonalizadoraList.clear()
            maquinaPersonalizadoraController.getPersonalizadora().collect { personalizadoras ->
                maquinaPersonalizadoraList.add(personalizadoras)
            }
            maquinaPersonalizadoraList.forEach { maquinas ->
                println(maquinas)
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
            productoDelete?.let {
                productoController.deleteProducto(it)
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
            adquisicionDelete?.let {
                adquisicionController.deleteAdquisicion(it)
            }

            //Encordados
            //GetById
            val encordado = encordarController.getEncordadoById(encordadosList[1].id)
            encordado?.let { println(it) }
            //Update
            encordado?.let {
                it.informacionEndordado = "Cuerdas de plástico"
                encordarController.updateEncordado(it)
            }
            //Delete
            val encordadoDelete = encordarController.getEncordadoById(encordadosList[0].id)
            encordadoDelete?.let {
                encordarController.deleteEncordado(it)
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
            personalizacionDelete?.let {
                personalizarController.deletePersonalizacion(it)
            }

            //Encordadora
            //GetById
            val encordadora = maquinaEncordadoraController.getEncordadoraById(maquinaEncordadoraList[1].id)
            encordadora?.let { println(it) }
            //Update
            encordadora?.let {
                it.isManual = false
                maquinaEncordadoraController.updateEncordadora(it)
            }
            //Delete
            val encordadoraDelete = maquinaEncordadoraController.getEncordadoraById(maquinaEncordadoraList[0].id)
            encordadoraDelete?.let {
                maquinaEncordadoraController.deleteEncordadora(it)
            }

            //Personalizadora
            //GetById
            val personalizadora =
                maquinaPersonalizadoraController.getPersonalizadoraById(maquinaPersonalizadoraList[0].id)
            personalizadora?.let { println(it) }
            //Update
            personalizadora?.let {
                it.rigidez = true
                maquinaPersonalizadoraController.updatePersonalizadora(it)
            }
            //Delete
            val personalizadoraDelete =
                maquinaPersonalizadoraController.getPersonalizadoraById(maquinaPersonalizadoraList[1].id)
            personalizadoraDelete?.let {
                maquinaPersonalizadoraController.deletePersonalizadora(it)
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
        MongoDbManager.database.getCollection<Encordadora>().drop()
        MongoDbManager.database.getCollection<Personalizadora>().drop()
    }
}

