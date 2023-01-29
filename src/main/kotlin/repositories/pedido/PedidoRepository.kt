package repositories.pedido

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import models.Pedido
import org.litote.kmongo.Id
import repositories.CrudRepository

/**
 * Interfaz a la que se le pasa la interfaz genérica del CRUD para la clase Pedido
 * @property CrudRepository
 */
interface PedidoRepository : CrudRepository<Pedido, Id<Pedido>> {
}