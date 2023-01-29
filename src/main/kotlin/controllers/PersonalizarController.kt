package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Personalizar
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
        return personalizarRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun createPersonalizacion(item: Personalizar): Personalizar {
        logger.debug { "Creando $item" }
        personalizarRepository.save(item)
        return item
    }

    suspend fun getPersonalizacionById(id: Id<Personalizar>): Personalizar? {
        logger.debug { "Buscando $id" }
        return personalizarRepository.findByID(id)
    }

    suspend fun updatePersonalizacion(item: Personalizar) {
        logger.debug { "Actualizando $item" }
        personalizarRepository.save(item)
    }

    suspend fun deletePersonalizacion(item: Personalizar): Boolean {
        logger.debug { "Borrando $item" }
        return personalizarRepository.delete(item)
    }
}