package ktor

import controllers.APIController
import db.MongoDbManager
import db.getAdquisicionInit
import kotlinx.coroutines.runBlocking
import models.Usuario
import service.cache.UsuariosCache

fun main(args: Array<String>): Unit = runBlocking {

    val cache = UsuariosCache()
    val controller =
        APIController(
            UsuariosCacheRepository(cache),
            UsuariosMongoRepository(),
            UsuariosKtorFitRepository(),
            TareasKtorFitRepository()
        )

    limpiarDatos()

    controller.getAllUsuariosApi().collect {
        controller.saveUsuarios(it)
        println(it)
    }

    getAdquisicionInit().forEach {
        controller.uploadAdquisicion(it)
        println(it)
    }

}

private suspend fun limpiarDatos() {
    if (MongoDbManager.database.getCollection<Usuario>().countDocuments() > 0) {
        MongoDbManager.database.getCollection<Usuario>().drop()
    }

}