package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import dto.toEncordarDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Encordar
import models.ResponseFailure
import models.ResponseSuccess
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.encordar.EncordarRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para los encordados
 *
 * @property encordarRepository
 */
@Single
class EncordarController(
    private val encordarRepository: EncordarRepository,
) {
    fun getEncordados(): Flow<Encordar> {
        logger.debug { "Obteniendo encordaciones" }
        val response = encordarRepository.findAll().flowOn(Dispatchers.IO)

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createEncordado(item: Encordar): Encordar {
        logger.debug { "Creando $item" }
        encordarRepository.save(item)

        println(Json.encodeToString(ResponseSuccess(201, item.toEncordarDto())))
        return item
    }

    suspend fun getEncordadoById(id: Id<Encordar>): Encordar? {
        logger.debug { "Buscando encordados por $id" }
        val response = encordarRepository.findByID(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Encordado not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toEncordarDto())))

        return response
    }

    suspend fun updateEncordado(item: Encordar) {
        logger.debug { "Actualizando $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toEncordarDto())))
        encordarRepository.save(item)
    }

    suspend fun deleteEncordado(item: Encordar): Boolean {
        logger.debug { "Borrando $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toEncordarDto())))
        return encordarRepository.delete(item)
    }
}