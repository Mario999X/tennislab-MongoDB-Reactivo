package repositories.adquisicion

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import models.Adquisicion
import org.litote.kmongo.Id
import repositories.CrudRepository

/**
 * Interfaz a la que se le pasa la interfaz genérica del CRUD para la clase Adquisiciones
 * @property CrudRepository
 */
interface AdquisicionRepository : CrudRepository<Adquisicion, Id<Adquisicion>>