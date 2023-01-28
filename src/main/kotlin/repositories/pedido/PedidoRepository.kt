package repositories.pedido

import models.Pedido
import org.litote.kmongo.Id
import repositories.CrudRepository

interface PedidoRepository : CrudRepository<Pedido, Id<Pedido>> {
}