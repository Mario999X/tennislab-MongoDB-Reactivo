package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import dto.toUsuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import models.Perfil
import repositories.tarea.TareasKtorFitRepository
import repositories.usuario.UsuariosCacheRepositoryImpl
import repositories.usuario.UsuariosKtorFitRepositoryImpl
import repositories.usuario.UsuariosMongoRepositoryImpl
import models.Tarea
import models.Usuario
import org.koin.core.annotation.Single
import org.litote.kmongo.Id

/**
 * Controlador encargado de las tareas y usuarios, ambos usan en parte la API
 *
 * @property usuariosCacheRepositoryImpl
 * @property usuariosMongoRepositoryImpl
 * @property usuariosKtorFitRepositoryImpl
 * @property tareasKtorFitRepository
 */
@Single
class APIController(
    private val usuariosCacheRepositoryImpl: UsuariosCacheRepositoryImpl,
    private val usuariosMongoRepositoryImpl: UsuariosMongoRepositoryImpl,
    private val usuariosKtorFitRepositoryImpl: UsuariosKtorFitRepositoryImpl,
    private val tareasKtorFitRepository: TareasKtorFitRepository
) {

    // USUARIOS
    suspend fun getAllUsuariosApi(): Flow<Usuario> = withContext(Dispatchers.IO) {
        val listado = mutableListOf<Usuario>()
        usuariosKtorFitRepositoryImpl.findAll().collect { listado.add(it.toUsuario("Hola1")) }
        return@withContext listado.asFlow()
    }

    suspend fun getAllUsuariosMongo(): Flow<Usuario> = withContext(Dispatchers.IO) {
        return@withContext usuariosMongoRepositoryImpl.findAll()
    }

    suspend fun getAllUsuariosCache(): Flow<Usuario> = withContext(Dispatchers.IO) {
        return@withContext usuariosCacheRepositoryImpl.findAll()
    }

    suspend fun saveUsuario(entity: Usuario) = withContext(Dispatchers.IO) {
        launch {
            usuariosCacheRepositoryImpl.save(entity)
        }

        launch {
            usuariosMongoRepositoryImpl.save(entity)
        }
        joinAll()
    }

    suspend fun getUsuarioById(id: Id<Usuario>): Usuario? {
        var userSearch = usuariosCacheRepositoryImpl.findByID(id)
        if (userSearch == null) {
            userSearch = usuariosMongoRepositoryImpl.findByID(id)
        }
        return userSearch
    }

    suspend fun deleteUsuario(entity: Usuario) = withContext(Dispatchers.IO) {
        launch {
            usuariosCacheRepositoryImpl.delete(entity)
        }

        launch {
            usuariosMongoRepositoryImpl.delete(entity)
        }

        joinAll()
    }

    // TAREAS
    suspend fun getAllTareas(): Flow<Tarea> = withContext(Dispatchers.IO) {
        return@withContext tareasKtorFitRepository.findAll()
    }

    suspend fun saveTarea(entity: Tarea) = withContext(Dispatchers.IO) {
        if (entity.usuario.perfil == Perfil.ENCORDADOR) {
            launch {
                tareasKtorFitRepository.save(entity)
            }

            launch {
                tareasKtorFitRepository.uploadTarea(entity)
            }

            joinAll()
        } else System.err.println("No ha sido posible almacenar $entity || El usuario debe de ser de tipo ${Perfil.ENCORDADOR.name}")
    }

    suspend fun getTareaById(id: Id<Tarea>): Tarea? {
        return tareasKtorFitRepository.findByID(id)
    }

    suspend fun deleteTarea(entity: Tarea): Boolean {
        return tareasKtorFitRepository.delete(entity)
    }
}