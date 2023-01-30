package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Perfil
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
        return maquinaEncordadoraRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun createEncordadora(item: Encordadora): Encordadora {
        logger.debug { "Creando $item)" }
        if (item.turno?.trabajador?.perfil != Perfil.ENCORDADOR && item.turno != null) {
            System.err.println("Problema al crear el turno, el usuario debe de ser de tipo ${Perfil.ENCORDADOR}")
            item.turno = null
        }
        maquinaEncordadoraRepository.save(item)
        return item
    }

    suspend fun getEncordadoraById(id: Id<Maquina>): Encordadora? {
        logger.debug { "Obteniendo encordadora por id $id" }
        return maquinaEncordadoraRepository.findByID(id)
    }

    suspend fun updateEncordadora(item: Encordadora) {
        logger.debug { "Actualizando $item" }
        if (item.turno?.trabajador?.perfil != Perfil.ENCORDADOR && item.turno != null) {
            System.err.println("Problema al crear el turno, el usuario debe de ser de tipo ${Perfil.ENCORDADOR}")
            item.turno = null
        }
        maquinaEncordadoraRepository.save(item)
    }

    suspend fun deleteEncordadora(item: Encordadora): Boolean {
        logger.debug { "Borrando $item" }
        return maquinaEncordadoraRepository.delete(item)
    }
}