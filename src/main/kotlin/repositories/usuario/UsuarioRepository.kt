package repositories.usuario

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import models.Usuario
import org.litote.kmongo.Id
import repositories.CrudRepository

/**
 * Interfaz a la que se le pasa la interfaz genérica del CRUD para la clase Usuario
 * @property CrudRepository
 */
interface UsuarioRepository : CrudRepository<Usuario, Id<Usuario>> {
}