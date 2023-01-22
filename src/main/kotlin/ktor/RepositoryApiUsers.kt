package ktor

import dto.UsuarioDto
import dto.toUsuario
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import service.cache.UsuariosCache

class RepositoryApiUsers(
    private val cacheUsuarios: UsuariosCache
) {
    // Inyectar dependencia
    private val client by lazy { KtorFitClient.instance }

    private var refreshJob: Job? = null // Job para cancelar la ejecución

    init {
        refreshCache()
    }

    suspend fun findAll(): Flow<UsuarioDto> = withContext(Dispatchers.IO) {
        val call = client.getAll()

        return@withContext call.asFlow()
    }

    private fun refreshCache() {
        if (refreshJob != null) refreshJob?.cancel()

        refreshJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                println("Refrescando cache usuarios")
                findAll().collect{
                    val user = it.toUsuario("Hola1")
                    cacheUsuarios.cache.put(user.uuid, user)
                }
                println("Cache actualizada: ${cacheUsuarios.cache.asMap().size}")
                cacheUsuarios.cache.asMap().forEach {
                    println(it) // El UUID es distinto
                }
                delay(cacheUsuarios.refreshTime.toLong())
            }
        }
    }
}