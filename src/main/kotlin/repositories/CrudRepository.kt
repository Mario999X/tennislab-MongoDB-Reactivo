package repositories

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz generica que se implementara en los repositorios; cuenta con las operaciones basicas.
 *
 * @param T
 * @param ID
 */
interface CrudRepository<T, ID> {
    fun findAll(): Flow<T>
    suspend fun findByID(id: ID): T?
    suspend fun save(entity: T): T?
    suspend fun delete(entity: T): Boolean
}