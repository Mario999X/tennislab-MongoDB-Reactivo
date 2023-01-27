package ktor

import dto.UsuarioDto
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class UsuariosKtorFitRepository {
    // Inyectar dependencia
    private val client by lazy { KtorFitClient.instance }

    suspend fun findAll(): Flow<UsuarioDto> = withContext(Dispatchers.IO) {
        val call = client.getAll()

        return@withContext call.asFlow()
    }

}