package ktorfit

import controllers.APIController
import controllers.TareaController
import db.MongoDbManager
import db.getAdquisicionInit
import db.getPersonalizaciones
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Perfil
import models.Tarea
import models.Usuario
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KtorFitApp : KoinComponent {

    fun run(): Unit = runBlocking {
        val limpiar = launch {
            limpiarDatos()
        }
        limpiar.join()

        val apiController: APIController by inject()
        val tareaController: TareaController by inject()

        val listadoUsers = apiController.getAllUsuariosApi().toList().toMutableList()
        listadoUsers[9].perfil = Perfil.ENCORDADOR
        listadoUsers[8].perfil = Perfil.ADMIN

        listadoUsers.forEach {
            apiController.saveUsuarios(it)
            println(it)
        }

        val tarea = Tarea(
            adquisicion = getAdquisicionInit()[1],
            personalizar = getPersonalizaciones()[1]
        )
        apiController.uploadTarea(tarea)
        tareaController.createTarea(tarea)
    }

    private suspend fun limpiarDatos() {
        if (MongoDbManager.database.getCollection<Usuario>().countDocuments() > 0) {
            MongoDbManager.database.getCollection<Usuario>().drop()
        }

    }

}