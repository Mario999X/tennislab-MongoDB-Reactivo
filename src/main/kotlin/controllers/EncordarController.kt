package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Encordar
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
        return encordarRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun createEncordados(item: Encordar): Encordar {
        logger.debug { "Creando $item" }
        encordarRepository.save(item)
        return item
    }

    suspend fun getEncordadoById(id: Id<Encordar>): Encordar? {
        logger.debug { "Buscando encordados por $id" }
        return encordarRepository.findByID(id)
    }

    suspend fun updateEncordado(item: Encordar) {
        logger.debug { "Actualizando $item" }
        encordarRepository.save(item)
    }

    suspend fun deleteEncordado(item: Encordar): Boolean {
        logger.debug { "Borrando $item" }
        return encordarRepository.delete(item)
    }
}