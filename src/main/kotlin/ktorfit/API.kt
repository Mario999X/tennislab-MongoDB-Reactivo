package ktorfit

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import controllers.APIController
import db.MongoDbManager
import db.getAdquisicionInit
import db.getEncordaciones
import db.getPersonalizaciones
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Perfil
import models.Tarea
import models.Usuario
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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

    }

    private suspend fun limpiarDatos() {
        if (MongoDbManager.database.getCollection<Usuario>().countDocuments() > 0) {
            MongoDbManager.database.getCollection<Usuario>().drop()
        }
        if (MongoDbManager.database.getCollection<Tarea>().countDocuments() > 0) {
            MongoDbManager.database.getCollection<Tarea>().drop()
        }

    }

}