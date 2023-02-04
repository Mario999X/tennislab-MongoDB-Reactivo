package repositories.usuario

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import dto.UsuarioDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import ktorfit.KtorFitClient

/**
 * Repositorio de Usuarios en la API
 */
class UsuariosKtorFitRepositoryImpl {

    private val client by lazy { KtorFitClient.instance }

    suspend fun findAll(): Flow<UsuarioDto> = withContext(Dispatchers.IO) {
        val call = client.getAll()

        return@withContext call.asFlow()
    }

}