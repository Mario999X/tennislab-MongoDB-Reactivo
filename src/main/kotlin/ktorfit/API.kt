package ktorfit

import controllers.APIController
import db.MongoDbManager
import db.getAdquisicionInit
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

        apiController.getAllUsuariosApi().collect {
            apiController.saveUsuarios(it)
            println(it)
        }

        getAdquisicionInit().forEach {
            apiController.uploadAdquisicion(it)
            println(it)
        }

    }

    private suspend fun limpiarDatos() {
        if (MongoDbManager.database.getCollection<Usuario>().countDocuments() > 0) {
            MongoDbManager.database.getCollection<Usuario>().drop()
        }

    }

}