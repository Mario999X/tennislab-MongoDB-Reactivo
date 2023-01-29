package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Pedido
import mu.KotlinLogging
import org.litote.kmongo.Id
import repositories.pedido.PedidoRepository


private val logger = KotlinLogging.logger { }

/**
 * Controlador encargado de "Pedidos" usando un repositorio
 *
 * @property pedidoRepository
 */
class PedidoController(
    private val pedidoRepository: PedidoRepository
) {
    fun getPedidos(): Flow<Pedido> {
        logger.debug { "Obteniendo pedidos" }
        return pedidoRepository.findAll().flowOn(Dispatchers.IO)
    }

    suspend fun createPedido(item: Pedido): Pedido {
        logger.debug { "Creando $item" }
        pedidoRepository.save(item)
        return item
    }

    suspend fun getPedidoById(id: Id<Pedido>): Pedido? {
        logger.debug { "Buscando $id" }
        return pedidoRepository.findByID(id)
    }

    suspend fun updatePedido(item: Pedido) {
        logger.debug { "Actualizando $item" }
        pedidoRepository.save(item)
    }

    suspend fun deletePedido(item: Pedido): Boolean {
        logger.debug { "Borrando $item" }
        return pedidoRepository.delete(item)
    }
}