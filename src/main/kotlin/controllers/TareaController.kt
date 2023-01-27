package controllers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Tarea
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.tarea.TareaRepository

private val logger = KotlinLogging.logger { }

@Single
class TareaController(
    private val tareaRepository: TareaRepository
) {

    fun getTareas(): Flow<Tarea> {
        logger.debug { "Obteniendo tareas" }
        return tareaRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun createTarea(item: Tarea): Tarea {
        logger.debug { "Creando tarea $item" }
        tareaRepository.save(item)
        return item
    }

    suspend fun getTareaById(id: Id<Tarea>): Tarea? {
        logger.debug { "Obteniendo tarea con id $id" }
        return tareaRepository.findByID(id)
    }

    suspend fun updateTarea(item: Tarea) {
        logger.debug { "Actualizando tarea $item" }
        tareaRepository.save(item)
    }

    suspend fun deleteTarea(item: Tarea): Boolean {
        logger.debug { "Borrando tarea $item" }
        return tareaRepository.delete(item)
    }
}