package repositories.usuario

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import dto.UsuarioDto
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ktorfit.KtorFitClient

/**
 * Repositorio de "Usuarios", se encarga unicamente de obtener a los usuarios del servicio externalizado.
 *
 */
class UsuariosKtorFitRepositoryImpl {
    // Inyectar dependencia
    private val client by lazy { KtorFitClient.instance }

    suspend fun findAll(): Flow<UsuarioDto> = withContext(Dispatchers.IO) {
        val call = client.getAll()

        return@withContext call.asFlow()
    }

}