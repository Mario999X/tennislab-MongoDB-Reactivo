package controllers

import dto.toUsuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import repositories.tarea.TareasKtorFitRepository
import repositories.usuario.UsuariosCacheRepositoryImpl
import repositories.usuario.UsuariosKtorFitRepositoryImpl
import repositories.usuario.UsuariosMongoRepositoryImpl
import models.Tarea
import models.Usuario
import org.koin.core.annotation.Single

@Single
class APIController(
    private val usuariosCacheRepositoryImpl: UsuariosCacheRepositoryImpl,
    private val usuariosMongoRepositoryImpl: UsuariosMongoRepositoryImpl,
    private val usuariosKtorFitRepositoryImpl: UsuariosKtorFitRepositoryImpl,
    private val tareasKtorFitRepository: TareasKtorFitRepository
) {

    suspend fun getAllUsuariosApi(): Flow<Usuario> = withContext(Dispatchers.IO) {
        val listado = mutableListOf<Usuario>()
        usuariosKtorFitRepositoryImpl.findAll().collect { listado.add(it.toUsuario("Hola1")) }
        return@withContext listado.asFlow()
    }

    suspend fun saveUsuarios(entity: Usuario) = withContext(Dispatchers.IO) {
        launch {
            usuariosCacheRepositoryImpl.save(entity)
        }

        launch {
            usuariosMongoRepositoryImpl.save(entity)
        }
        joinAll()
    }

    suspend fun uploadTarea(entity: Tarea) = withContext(Dispatchers.IO) {
        launch {
            tareasKtorFitRepository.uploadTarea(entity)
        }
    }

    suspend fun saveTarea(entity: Tarea) = withContext(Dispatchers.IO) {
        launch {
            tareasKtorFitRepository.save(entity)
        }
    }
}