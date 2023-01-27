package controllers

import dto.toUsuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ktor.TareasKtorFitRepository
import ktor.UsuariosCacheRepository
import ktor.UsuariosKtorFitRepository
import ktor.UsuariosMongoRepository
import models.Adquisicion
import models.Usuario

class APIController(
    private val usuariosCacheRepository: UsuariosCacheRepository,
    private val usuariosMongoRepository: UsuariosMongoRepository,
    private val usuariosKtorFitRepository: UsuariosKtorFitRepository,
    private val tareasKtorFitRepository: TareasKtorFitRepository
) {

    suspend fun getAllUsuariosApi(): Flow<Usuario> = withContext(Dispatchers.IO) {
        val listado = mutableListOf<Usuario>()
        usuariosKtorFitRepository.findAll().collect { listado.add(it.toUsuario("Hola1")) }
        return@withContext listado.asFlow()
    }

    suspend fun saveUsuarios(entity: Usuario) = withContext(Dispatchers.IO) {
        launch {
            usuariosCacheRepository.save(entity)
        }

        launch {
            usuariosMongoRepository.save(entity)
        }
        joinAll()
    }

    suspend fun uploadAdquisicion(entity: Adquisicion) = withContext(Dispatchers.IO) {
        launch {
            tareasKtorFitRepository.uploadAdquisicion(entity)
        }
    }
}