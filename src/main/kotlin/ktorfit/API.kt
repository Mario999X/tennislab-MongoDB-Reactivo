package ktorfit

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import controllers.APIController
import controllers.PedidoController
import db.MongoDbManager
import db.getAdquisicionInit
import db.getEncordaciones
import db.getPersonalizaciones
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime

/**
 * Una de las APPs principales, encargada de realizar conexion con la API (Usuarios-Tareas) y de cachear usuarios
 *
 */
class KtorFitApp : KoinComponent {

    fun run(): Unit = runBlocking {
        val limpiar = launch {
            limpiarDatos()
        }
        limpiar.join()

        val apiController: APIController by inject()
        val pedidoController: PedidoController by inject()

        //Usuarios
        val listadoUsers = apiController.getAllUsuariosApi().toList().toMutableList()
        listadoUsers[9].perfil = Perfil.ENCORDADOR
        listadoUsers[8].perfil = Perfil.ADMIN

        listadoUsers.forEach {
            apiController.saveUsuarios(it)
            println(it)
        }

        //CRUD
        //Tareas
        val tarea1 = Tarea(
            adquisicion = getAdquisicionInit()[1],
            personalizar = getPersonalizaciones()[1],
            usuario = apiController.getUsuarioById(listadoUsers[9].id)!!
        )
        val tarea2 = Tarea(
            encordar = getEncordaciones()[1],
            usuario = apiController.getUsuarioById(listadoUsers[9].id)!!
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

    private suspend fun limpiarDatos() {
        if (MongoDbManager.database.getCollection<Usuario>().countDocuments() > 0) {
            MongoDbManager.database.getCollection<Usuario>().drop()
        }
        if (MongoDbManager.database.getCollection<Tarea>().countDocuments() > 0) {
            MongoDbManager.database.getCollection<Tarea>().drop()
        }
        if (MongoDbManager.database.getCollection<Pedido>().countDocuments() > 0) {
            MongoDbManager.database.getCollection<Pedido>().drop()
        }

    }

}