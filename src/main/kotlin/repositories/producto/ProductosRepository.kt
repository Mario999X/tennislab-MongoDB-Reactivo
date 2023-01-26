package repositories.producto

import models.Producto
import org.litote.kmongo.Id
import repositories.CrudRepository

interface ProductosRepository : CrudRepository<Producto, Id<Producto>>