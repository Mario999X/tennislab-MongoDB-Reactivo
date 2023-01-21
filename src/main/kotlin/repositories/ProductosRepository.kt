package repositories

import models.Producto
import org.litote.kmongo.Id

interface ProductosRepository : CrudRepository<Producto, Id<Producto>> {
}