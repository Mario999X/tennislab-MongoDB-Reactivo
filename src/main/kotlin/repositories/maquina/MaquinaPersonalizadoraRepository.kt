package repositories.maquina

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import models.maquina.Maquina
import models.maquina.Personalizadora
import org.litote.kmongo.Id
import repositories.CrudRepository

/**
 * Interfaz a la que se le pasa la interfaz genérica del CRUD para la clase Personalizadora
 * @property CrudRepository
 */
interface MaquinaPersonalizadoraRepository : CrudRepository<Personalizadora, Id<Maquina>> {
}