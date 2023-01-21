package repositories

import kotlinx.coroutines.flow.Flow

interface CrudRepository<T, ID> {
    fun findAll(): Flow<T>
    suspend fun findByID(id: ID): T?
    suspend fun save(entity: T): T?
    suspend fun delete(entity: T): Boolean
}