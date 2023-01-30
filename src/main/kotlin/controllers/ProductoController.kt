package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */

import com.mongodb.reactivestreams.client.ChangeStreamPublisher
import dto.toProductoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Producto
import models.ResponseFailure
import models.ResponseSuccess
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.producto.ProductosRepository
import service.ProductoService

private val logger = KotlinLogging.logger { }

/**
 * Controlador al que se le pasa un repositorio y un servicio, el cual contiene el CRUD para los productos
 * @param productosRepository
 * @param productoService
 */
@Single
class ProductoController(
    private val productosRepository: ProductosRepository,
    private val productoService: ProductoService
) {
    fun getProductos(): Flow<Producto> {
        logger.debug { "Obteniendo productos" }
        val response = productosRepository.findAll().flowOn(Dispatchers.IO)

        println(Json.encodeToString(ResponseSuccess(200, response.toString())))
        return response
    }

    fun watchProducto(): ChangeStreamPublisher<Producto> {
        logger.debug { "Cambios en producto" }
        return productoService.watch()
    }

    suspend fun createProducto(item: Producto): Producto {
        logger.debug { "Creando producto $item" }
        productosRepository.save(item)

        println(Json.encodeToString(ResponseSuccess(201, item.toProductoDto())))
        return item
    }

    suspend fun getProductoById(id: Id<Producto>): Producto? {
        logger.debug { "Obteniendo producto con id $id" }
        val producto = productosRepository.findByID(id)

        if (producto == null) {
            System.err.println(Json.encodeToString(ResponseFailure(404, "Producto not found")))
        } else println(Json.encodeToString(ResponseSuccess(200, producto.toProductoDto())))

        return producto
    }

    suspend fun updateProducto(item: Producto) {
        logger.debug { "Actualizando producto $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toProductoDto())))
        productosRepository.save(item)
    }

    suspend fun deleteProducto(item: Producto): Boolean {
        logger.debug { "Borrando producto $item" }

        println(Json.encodeToString(ResponseSuccess(200, item.toProductoDto())))
        return productosRepository.delete(item)
    }
}