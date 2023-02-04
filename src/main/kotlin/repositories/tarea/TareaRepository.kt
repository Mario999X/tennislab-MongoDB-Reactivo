package repositories.tarea

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import models.Tarea
import org.litote.kmongo.Id
import repositories.CrudRepository

/**
 * Interfaz a la que se le pasa la interfaz genérica del CRUD para la clase Tarea
 * @property CrudRepository
 */
interface TareaRepository : CrudRepository<Tarea, Id<Tarea>> {
}