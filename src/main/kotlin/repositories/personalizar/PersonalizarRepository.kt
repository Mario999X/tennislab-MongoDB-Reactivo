package repositories.personalizar

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import models.Personalizar
import org.litote.kmongo.Id
import repositories.CrudRepository

/**
 * Interfaz a la que se le pasa la interfaz genérica del CRUD para la clase Personalizar
 * @property CrudRepository
 */
interface PersonalizarRepository : CrudRepository<Personalizar, Id<Personalizar>> {
}