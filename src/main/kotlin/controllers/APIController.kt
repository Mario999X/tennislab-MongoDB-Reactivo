package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import dto.toTareaDto
import dto.toUsuario
import dto.toUsuarioDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.*
import repositories.tarea.TareasKtorFitRepository
import repositories.usuario.UsuariosCacheRepositoryImpl
import repositories.usuario.UsuariosKtorFitRepositoryImpl
import repositories.usuario.UsuariosMongoRepositoryImpl
import org.koin.core.annotation.Single
import org.litote.kmongo.Id

/**
 * Controlador encargado de las tareas y usuarios, ambos usan en parte la API
 *
 * @param usuariosCacheRepositoryImpl
 * @param usuariosMongoRepositoryImpl
 * @param usuariosKtorFitRepositoryImpl
 * @param tareasKtorFitRepository
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

        println(Json.encodeToString(ResponseSuccess(200, listado.toString())))
        return@withContext listado.asFlow()
    }

    suspend fun getAllUsuariosMongo(): Flow<Usuario> = withContext(Dispatchers.IO) {
        val response = usuariosMongoRepositoryImpl.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return@withContext response
    }

    suspend fun getAllUsuariosCache(): Flow<Usuario> = withContext(Dispatchers.IO) {
        val response = usuariosCacheRepositoryImpl.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return@withContext response
    }

    suspend fun saveUsuario(entity: Usuario) = withContext(Dispatchers.IO) {
        launch {
            val response = usuariosCacheRepositoryImpl.save(entity)
            println(Json.encodeToString(ResponseSuccess(201, response.toUsuarioDto())))
        }

        launch {
            val response = usuariosMongoRepositoryImpl.save(entity)
            println(Json.encodeToString(ResponseSuccess(201, response.toUsuarioDto())))
        }
        joinAll()
    }

    suspend fun getUsuarioById(id: Id<Usuario>): Usuario? {
        var userSearch = usuariosCacheRepositoryImpl.findByID(id)
        if (userSearch != null) {
            println(Json.encodeToString(ResponseSuccess(200, userSearch.toUsuarioDto())))

        } else {
            userSearch = usuariosMongoRepositoryImpl.findByID(id)
            if (userSearch != null) {
                println(Json.encodeToString(ResponseSuccess(201, userSearch.toUsuarioDto())))
            } else System.err.println(Json.encodeToString(ResponseFailure(404, "User not found")))
        }

        return userSearch
    }

    suspend fun deleteUsuario(entity: Usuario) = withContext(Dispatchers.IO) {
        launch {
            usuariosCacheRepositoryImpl.delete(entity)
            println(Json.encodeToString(ResponseSuccess(200, entity.toUsuarioDto())))
        }

        launch {
            usuariosMongoRepositoryImpl.delete(entity)
            println(Json.encodeToString(ResponseSuccess(200, entity.toUsuarioDto())))
        }

        joinAll()
    }

    // TAREAS
    suspend fun getAllTareas(): Flow<Tarea> = withContext(Dispatchers.IO) {
        val response = tareasKtorFitRepository.findAll()

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return@withContext response
    }

    suspend fun saveTarea(entity: Tarea) = withContext(Dispatchers.IO) {
        if (entity.usuario.perfil == Perfil.ENCORDADOR) {
            launch {
                tareasKtorFitRepository.save(entity)
                println(Json.encodeToString(ResponseSuccess(201, entity.toTareaDto())))
            }

            launch {
                tareasKtorFitRepository.uploadTarea(entity)
                println(println(Json.encodeToString(ResponseSuccess(201, entity.toTareaDto()))))
            }

            joinAll()
        } else System.err.println(
            Json.encodeToString(
                ResponseFailure(
                    400,
                    "No ha sido posible almacenar $entity || El usuario debe de ser de tipo ${Perfil.ENCORDADOR.name}"
                )
            )
        )
    }

    suspend fun getTareaById(id: Id<Tarea>): Tarea? {
        val response = tareasKtorFitRepository.findByID(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Tarea not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toTareaDto())))

        return response
    }

    suspend fun deleteTarea(entity: Tarea): Boolean {

        println(Json.encodeToString(ResponseSuccess(200, entity.toTareaDto())))
        return tareasKtorFitRepository.delete(entity)
    }
}