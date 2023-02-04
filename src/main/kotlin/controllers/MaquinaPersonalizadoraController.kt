package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import dto.toPersonalizadoraDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Perfil
import models.ResponseFailure
import models.ResponseSuccess
import models.maquina.Maquina
import models.maquina.Personalizadora
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.maquina.MaquinaPersonalizadoraRepository

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para las personalizadoras
 * @param maquinaPersonalizadoraRepository
 */
@Single
class MaquinaPersonalizadoraController(private val maquinaPersonalizadoraRepository: MaquinaPersonalizadoraRepository) {
    fun getPersonalizadoras(): Flow<Personalizadora> {
        logger.debug { "Obteniendo máquinas personalizadoras" }
        val response = maquinaPersonalizadoraRepository.findAll().flowOn(Dispatchers.IO)
        println(Json.encodeToString(ResponseSuccess(200, response.toString())))

        return response
    }

    suspend fun createPersonalizadora(item: Personalizadora): Personalizadora {
        logger.debug { "Creando $item" }
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
        } else println(Json.encodeToString(ResponseSuccess(200, item.toPersonalizadoraDto())))
        maquinaPersonalizadoraRepository.save(item)

        return item
    }

    suspend fun getPersonalizadoraById(id: Id<Maquina>): Personalizadora? {
        logger.debug { "Obteniendo personalizadora por id $id" }
        val response = maquinaPersonalizadoraRepository.findByID(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Personalizadora not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toPersonalizadoraDto())))

        return response
    }

    suspend fun updatePersonalizadora(item: Personalizadora) {
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
        } else println(Json.encodeToString(ResponseSuccess(200, item.toPersonalizadoraDto())))

        maquinaPersonalizadoraRepository.save(item)
    }

    suspend fun deletePersonalizadora(item: Personalizadora): Boolean {
        logger.debug { "Borrando $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toPersonalizadoraDto())))
        return maquinaPersonalizadoraRepository.delete(item)
    }
}