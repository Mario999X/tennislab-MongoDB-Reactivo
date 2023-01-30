package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import dto.toPedidoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Pedido
import models.ResponseFailure
import models.ResponseSuccess
import mu.KotlinLogging
import org.litote.kmongo.Id
import repositories.pedido.PedidoRepository


private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio, el cual contiene el CRUD para los pedidos
 * @param pedidoRepository
 */
class PedidoController(
    private val pedidoRepository: PedidoRepository
) {
    fun getPedidos(): Flow<Pedido> {
        logger.debug { "Obteniendo pedidos" }
        val response = pedidoRepository.findAll().flowOn(Dispatchers.IO)

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    suspend fun createPedido(item: Pedido): Pedido {
        logger.debug { "Creando $item" }
        pedidoRepository.save(item)

        println(Json.encodeToString(ResponseSuccess(201, item.toPedidoDto())))
        return item
    }

    suspend fun getPedidoById(id: Id<Pedido>): Pedido? {
        logger.debug { "Buscando $id" }
        val response = pedidoRepository.findByID(id)

        if (response == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Pedido not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, response.toPedidoDto())))

        return response
    }

    suspend fun updatePedido(item: Pedido) {
        logger.debug { "Actualizando $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toPedidoDto())))
        pedidoRepository.save(item)
    }

    suspend fun deletePedido(item: Pedido): Boolean {
        logger.debug { "Borrando $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toPedidoDto())))
        return pedidoRepository.delete(item)
    }
}