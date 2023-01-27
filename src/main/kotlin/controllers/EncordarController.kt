package controllers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Encordar
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.encordar.EncordarRepository

private val logger = KotlinLogging.logger { }

@Single
class EncordarController(
    private val encordarRepository: EncordarRepository,
) {
    fun getEncordaciones(): Flow<Encordar> {
        logger.debug { "Obteniendo encordaciones" }
        return encordarRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun createEncordacion(item: Encordar): Encordar {
        logger.debug { "Creando $item" }
        encordarRepository.save(item)
        return item
    }

    suspend fun getEncordacionById(id: Id<Encordar>): Encordar? {
        logger.debug { "Buscando $id" }
        return encordarRepository.findByID(id)
    }

    suspend fun updateEncordacion(item: Encordar) {
        logger.debug { "Update $item" }
        encordarRepository.save(item)
    }

    suspend fun deleteEncordacion(item: Encordar): Boolean {
        logger.debug { "Borrando $item" }
        return encordarRepository.delete(item)
    }
}