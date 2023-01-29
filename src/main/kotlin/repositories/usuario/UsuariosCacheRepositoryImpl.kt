package repositories.usuario

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import models.Usuario
import org.litote.kmongo.Id
import service.cache.UsuariosCache

/**
 * Repositorio de la caché, realiza operaciones CRUD básicas
 *@property UsuarioRepository
 */
class UsuariosCacheRepositoryImpl : UsuarioRepository {

    private val cacheUsuarios = UsuariosCache()
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
        val usuario = cacheUsuarios.cache.asMap()[entity.id]
        if (usuario != null) {
            listaBusquedas.removeIf { it.id == usuario.id }
            cacheUsuarios.cache.invalidate(entity.id)
            existe = true
        }
        return existe
    }

    override suspend fun save(entity: Usuario): Usuario {
        println("\tsaveCache")
        listaBusquedas.add(entity)
        return entity
    }

    override suspend fun findByID(id: Id<Usuario>): Usuario? {
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
                        cacheUsuarios.cache.put(user.id, user)
                    }

                    listaBusquedas.clear()

                    println("Cache actualizada: ${cacheUsuarios.cache.asMap().size}")
                }
                delay(cacheUsuarios.refreshTime.toLong())
            }
        }
    }
}