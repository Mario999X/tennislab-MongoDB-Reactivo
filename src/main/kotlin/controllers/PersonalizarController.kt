package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */

import dto.toPersonalizarDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Personalizar
import models.ResponseFailure
import models.ResponseSuccess
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.personalizar.PersonalizarRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para las personalizaciones
 * @param personalizarRepository
 */
@Single
class PersonalizarController(
    private val personalizarRepository: PersonalizarRepository
) {
    fun getPersonalizaciones(): Flow<Personalizar> {
        logger.debug { "Obteniendo personalizaciones" }
        val response = personalizarRepository.findAll().flowOn(Dispatchers.IO)

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createPersonalizacion(item: Personalizar): Personalizar {
        logger.debug { "Creando $item" }
        personalizarRepository.save(item)

        println(Json.encodeToString(ResponseSuccess(201, item.toPersonalizarDto())))
        return item
    }

    suspend fun getPersonalizacionById(id: Id<Personalizar>): Personalizar? {
        logger.debug { "Buscando $id" }
        val response = personalizarRepository.findByID(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Personalizacion not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toPersonalizarDto())))

        return response
    }

    suspend fun updatePersonalizacion(item: Personalizar) {
        logger.debug { "Actualizando $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toPersonalizarDto())))
        personalizarRepository.save(item)
    }

    suspend fun deletePersonalizacion(item: Personalizar): Boolean {
        logger.debug { "Borrando $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toPersonalizarDto())))
        return personalizarRepository.delete(item)
    }
}