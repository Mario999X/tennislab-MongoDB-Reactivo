package repositories.producto

/**
 * @author Mario Resa y Sebastián Mendoza
 */

import models.Producto
import org.litote.kmongo.Id
import repositories.CrudRepository

/**
 * Interfaz a la que se le pasa la interfaz genérica del CRUD para la clase Producto
 * @property CrudRepository
 */
interface ProductosRepository : CrudRepository<Producto, Id<Producto>>