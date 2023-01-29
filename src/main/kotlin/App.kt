/**
 * @author Mario Resa y Sebastián Mendoza
 */
import controllers.*
import db.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.collect
import models.*
import models.maquina.Encordadora
import models.maquina.Personalizadora
import models.producto.Producto
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime

private val logger = KotlinLogging.logger { }

/**
 * App donde se realiza el CRUD de la aplicación, de realizar conexión con la API (Usuarios-Tareas) y de cachear usuarios
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
        val apiController: APIController by inject()
        val pedidoController: PedidoController by inject()

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

        //Usuarios
        val listadoUsers = apiController.getAllUsuariosApi().toList().toMutableList()
        listadoUsers[0].raqueta = getRaquetasInit()
        listadoUsers[9].perfil = Perfil.ENCORDADOR
        listadoUsers[8].perfil = Perfil.ADMIN

        // Create CACHE-MONGO
        listadoUsers.forEach {
            apiController.saveUsuario(it)
            println(it)
        }
        // FindAll CACHE-MONGO
        apiController.getAllUsuariosCache().collect {
            println(it)
        }
        apiController.getAllUsuariosMongo().collect {
            println(it)
        }
        // FindById -> Cache -> Mongo
        val userId = apiController.getUsuarioById(listadoUsers[1].id)
        val userId2 = apiController.getUsuarioById(listadoUsers[2].id)
        println(userId)
        println(userId2)
        // Update -> Cache && Mongo
        userId?.let {
            it.name = "Solid Snake"
            apiController.saveUsuario(it)
        }
        // Delete -> Cache && Mongo
        userId2?.let {
            apiController.deleteUsuario(it)
        }

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
                it.turno = Turno(
                    horario = TipoHorario.TARDE,
                    trabajador = apiController.getUsuarioById(listadoUsers[9].id)!!
                )
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

            //Tareas
            val tarea1 = Tarea(
                adquisicion = getAdquisicionInit()[1],
                personalizar = getPersonalizaciones()[1],
                usuario = apiController.getUsuarioById(listadoUsers[9].id)!!
            )
            val tarea2 = Tarea(
                encordar = getEncordaciones()[1],
                usuario = apiController.getUsuarioById(listadoUsers[9].id)!!,
                raqueta = apiController.getUsuarioById(listadoUsers[0].id)!!.raqueta?.get(0)
            )
            //Create
            apiController.saveTarea(tarea1)
            apiController.saveTarea(tarea2)
            //FindAll
            apiController.getAllTareas().collect { item -> println(item) }
            //FindById
            val tareaId = apiController.getTareaById(tarea1.id)
            println(tareaId)
            //Update
            tareaId?.let {
                it.precio += 10.0
                apiController.saveTarea(it)
            }
            println(tareaId)
            //Delete
            tareaId?.let {
                apiController.deleteTarea(it)
            }

            // Pedidos
            val pedido = Pedido(
                estadoPedido = EstadoPedido.PROCESANDO,
                fechaEntrada = LocalDateTime.now().toString(),
                fechaProgramada = LocalDateTime.now().plusDays(10).toString(),
                cliente = apiController.getUsuarioById(listadoUsers[0].id)!!,
                tareas = apiController.getAllTareas().toList()
            )
            val pedido2 = Pedido(
                estadoPedido = EstadoPedido.TERMINADO,
                fechaEntrada = LocalDateTime.now().minusDays(5).toString(),
                fechaProgramada = LocalDateTime.now().plusDays(7).toString(),
                cliente = apiController.getUsuarioById(listadoUsers[0].id)!!,
                tareas = apiController.getAllTareas().toList()
            )
            // Create
            pedidoController.createPedido(pedido)
            pedidoController.createPedido(pedido2)
            // FindAll
            pedidoController.getPedidos().collect { println(it) }
            // FindById
            val pedidoId = pedidoController.getPedidoById(pedido.id)
            println(pedidoId)
            // Update
            pedidoId?.let {
                it.fechaSalida = LocalDateTime.now().plusDays(11).toString()
                pedidoController.updatePedido(it)
            }
            // Delete
            pedidoController.deletePedido(pedido2)
        }

        update.join()

        //Terminando los escuchadores
        escuchadorProducto.cancel()
    }

    // Si faltan los IF puede dar un aviso en la ejecucion
    suspend fun limpiarDatos() = withContext(Dispatchers.IO) {
        logger.debug { "Borrando datos de la base de datos" }
        MongoDbManager.database.getCollection<Producto>().drop()
        MongoDbManager.database.getCollection<Adquisicion>().drop()
        MongoDbManager.database.getCollection<Encordar>().drop()
        MongoDbManager.database.getCollection<Personalizar>().drop()
        MongoDbManager.database.getCollection<Encordadora>().drop()
        MongoDbManager.database.getCollection<Personalizadora>().drop()
        MongoDbManager.database.getCollection<Usuario>().drop()
        MongoDbManager.database.getCollection<Tarea>().drop()
        MongoDbManager.database.getCollection<Pedido>().drop()
    }
}

