package repositories.encordar

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import models.Encordar
import org.litote.kmongo.Id
import repositories.CrudRepository
/**
 * Interfaz a la que se le pasa la interfaz genérica del CRUD para la clase Encordar
 * @property CrudRepository
 */
interface EncordarRepository : CrudRepository<Encordar, Id<Encordar>> {
}