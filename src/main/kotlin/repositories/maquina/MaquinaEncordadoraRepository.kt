package repositories.maquina

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import models.maquina.Encordadora
import models.maquina.Maquina
import org.litote.kmongo.Id
import repositories.CrudRepository

/**
 * Interfaz a la que se le pasa la interfaz genérica del CRUD para la clase Encordadora
 * @property CrudRepository
 */
interface MaquinaEncordadoraRepository : CrudRepository<Encordadora, Id<Maquina>> {
}