package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import dto.toAdquisicionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Adquisicion
import models.ResponseFailure
import models.ResponseSuccess
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
        val response = adquisicionRepository.findAll().flowOn(Dispatchers.IO)

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createAdquisicion(item: Adquisicion): Adquisicion {
        logger.debug { "Creando adquisición $item" }
        adquisicionRepository.save(item)

        println(Json.encodeToString(ResponseSuccess(201, item.toAdquisicionDto())))
        return item
    }

    suspend fun getAdquisicionById(id: Id<Adquisicion>): Adquisicion? {
        logger.debug { "Obteniendo adquisición con id $id" }
        val response = adquisicionRepository.findByID(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Adquisicion not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toAdquisicionDto())))
        return response
    }

    suspend fun updateAdquisicion(item: Adquisicion) {
        logger.debug { "Actualizando adquisicion $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toAdquisicionDto())))
        adquisicionRepository.save(item)
    }

    suspend fun deleteAdquisicion(item: Adquisicion): Boolean {
        logger.debug { "Borrando adquisicion $item" }

        println(Json.encodeToString(ResponseSuccess(201, item.toAdquisicionDto())))
        return adquisicionRepository.delete(item)
    }
}