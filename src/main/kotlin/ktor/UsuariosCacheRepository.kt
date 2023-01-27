package ktor

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Usuario
import repositories.CrudRepository
import service.cache.UsuariosCache
import java.util.UUID

class UsuariosCacheRepository(
    private val cacheUsuarios: UsuariosCache
) : CrudRepository<Usuario, UUID> {

    private var refreshJob: Job? = null

    private var listaBusquedas = mutableListOf<Usuario>()

    init {
        refreshCache()
    }

    override fun findAll(): Flow<Usuario> {
        println("\tfindAllCache")
        return cacheUsuarios.cache.asMap().values.asFlow()
    }

    override suspend fun delete(entity: Usuario): Boolean {
        println("\tdeleteCache")
        var existe = false
        val usuario = cacheUsuarios.cache.asMap()[entity.uuid]
        if (usuario != null) {
            listaBusquedas.removeIf { it.id == usuario.id }
            cacheUsuarios.cache.invalidate(entity.uuid)
            existe = true
        }
        return existe
    }

    override suspend fun save(entity: Usuario): Usuario {
        println("\tsaveCache")
        listaBusquedas.add(entity)
        return entity
    }

    override suspend fun findByID(id: UUID): Usuario? {
        println("\tfindByIDCache")
        var usuario: Usuario? = null

        cacheUsuarios.cache.asMap().forEach {
            if (it.key == id) {
                usuario = it.value
            }
        }
        return usuario
    }

    private fun refreshCache() {
        if (refreshJob != null) refreshJob?.cancel()

        refreshJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                println("Refrescando cache usuarios")
                if (listaBusquedas.isNotEmpty()) {
                    listaBusquedas.forEach {
                        val user = it
                        cacheUsuarios.cache.put(user.uuid, user)
                    }

                    listaBusquedas.clear()

                    println("Cache actualizada: ${cacheUsuarios.cache.asMap().size}")
                }
                delay(cacheUsuarios.refreshTime.toLong())
            }
        }
    }
}