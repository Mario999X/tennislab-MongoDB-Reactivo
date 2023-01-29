package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
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
    fun getPersonalizadora(): Flow<Personalizadora> {
        logger.debug { "Obteniendo máquinas personalizadoras" }
        return maquinaPersonalizadoraRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun createPersonalizadora(item: Personalizadora): Personalizadora {
        logger.debug { "Creando $item" }
        maquinaPersonalizadoraRepository.save(item)
        return item
    }

    suspend fun getPersonalizadoraById(id: Id<Maquina>): Personalizadora? {
        logger.debug { "Obteniendo personalizadora por id $id" }
        return maquinaPersonalizadoraRepository.findByID(id)
    }

    suspend fun updatePersonalizadora(item: Personalizadora) {
        logger.debug { "Actualizando $item" }
        maquinaPersonalizadoraRepository.save(item)
    }

    suspend fun deletePersonalizadora(item: Personalizadora): Boolean {
        logger.debug { "Borrando $item" }
        return maquinaPersonalizadoraRepository.delete(item)
    }
}