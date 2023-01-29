package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Adquisicion
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.adquisicion.AdquisicionRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para las adquisiciones
 * @param adquisicionRepository
 */
@Single
class AdquisicionController(
    private val adquisicionRepository: AdquisicionRepository,
) {
    fun getAdquisiciones(): Flow<Adquisicion> {
        logger.debug { "Obteniendo adquisiciones" }
        return adquisicionRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun createAdquisicion(item: Adquisicion): Adquisicion {
        logger.debug { "Creando adquisición $item" }
        adquisicionRepository.save(item)
        return item
    }

    suspend fun getAdquisicionById(id: Id<Adquisicion>): Adquisicion? {
        logger.debug { "Obteniendo adquisición con id $id" }
        return adquisicionRepository.findByID(id)
    }

    suspend fun updateAdquisicion(item: Adquisicion) {
        logger.debug { "Actualizando adquisicion $item" }
        adquisicionRepository.save(item)
    }

    suspend fun deleteAdquisicion(item: Adquisicion): Boolean {
        logger.debug { "Borrando adquisicion $item" }
        return adquisicionRepository.delete(item)
    }
}