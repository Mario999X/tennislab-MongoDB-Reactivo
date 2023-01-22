package controllers

import com.mongodb.reactivestreams.client.ChangeStreamPublisher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Adquisicion
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.adquisicion.AdquisicionRepository
import service.AdquisicionService

private val logger = KotlinLogging.logger { }

@Single
@Named("AdquisicionController")
class AdquisicionController(
    @Named("AdquisicionRepository") private val adquisicionRepository: AdquisicionRepository,
    private val adquisicionService: AdquisicionService
) {
    suspend fun getAdquisiciones(): Flow<Adquisicion> {
        logger.debug { "Obteniendo adquisiciones" }
        return adquisicionRepository.findAll().flowOn(Dispatchers.IO)
    }

    fun watchAdquisicion(): ChangeStreamPublisher<Adquisicion> {
        logger.debug { "Cambios en adquisicion" }
        return adquisicionService.watch()
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