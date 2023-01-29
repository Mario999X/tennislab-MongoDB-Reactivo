package repositories

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz genérica que se implementara en los repositorios; cuenta con las operaciones básicas.
 * @param T Genérico de la clase a implementar
 * @param ID Genérico del tipo de variable a implementar
 */
interface CrudRepository<T, ID> {
    fun findAll(): Flow<T>
    suspend fun findByID(id: ID): T?
    suspend fun save(entity: T): T?
    suspend fun delete(entity: T): Boolean
}