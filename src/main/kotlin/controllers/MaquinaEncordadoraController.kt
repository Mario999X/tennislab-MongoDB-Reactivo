package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import dto.toEncordadoraDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Perfil
import models.ResponseFailure
import models.ResponseSuccess
import models.maquina.Encordadora
import models.maquina.Maquina
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.maquina.MaquinaEncordadoraRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para las encordadoras
 * @param maquinaEncordadoraRepository
 */
@Single
class MaquinaEncordadoraController(private val maquinaEncordadoraRepository: MaquinaEncordadoraRepository) {
    fun getEncordadoras(): Flow<Encordadora> {
        logger.debug { "Obteniendo máquinas encordadoras" }
        val response = maquinaEncordadoraRepository.findAll().flowOn(Dispatchers.IO)

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createEncordadora(item: Encordadora): Encordadora {
        logger.debug { "Creando $item)" }
        if (item.turno?.trabajador?.perfil != Perfil.ENCORDADOR && item.turno != null) {
            System.err.println(
                Json.encodeToString(
                    ResponseFailure(
                        400,
                        "Problema al crear el turno, el usuario debe de ser de tipo ${Perfil.ENCORDADOR}"
                    )
                )
            )
            item.turno = null
        } else println(Json.encodeToString(ResponseSuccess(200, item.toEncordadoraDto())))

        maquinaEncordadoraRepository.save(item)
        return item
    }

    suspend fun getEncordadoraById(id: Id<Maquina>): Encordadora? {
        logger.debug { "Obteniendo encordadora por id $id" }
        val response = maquinaEncordadoraRepository.findByID(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Encordadora not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toEncordadoraDto())))

        return response
    }

    suspend fun updateEncordadora(item: Encordadora) {
        logger.debug { "Actualizando $item" }
        if (item.turno?.trabajador?.perfil != Perfil.ENCORDADOR && item.turno != null) {
            System.err.println(
                Json.encodeToString(
                    ResponseFailure(
                        400,
                        "Problema al crear el turno, el usuario debe de ser de tipo ${Perfil.ENCORDADOR}"
                    )
                )
            )
            item.turno = null
        } else println(Json.encodeToString(ResponseSuccess(200, item.toEncordadoraDto())))

        maquinaEncordadoraRepository.save(item)
    }

    suspend fun deleteEncordadora(item: Encordadora): Boolean {
        logger.debug { "Borrando $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toEncordadoraDto())))
        return maquinaEncordadoraRepository.delete(item)
    }
}