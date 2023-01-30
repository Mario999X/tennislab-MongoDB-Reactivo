package controllers

/**
 * @author Mario Resa y Sebastián Mendoza
 */

import com.mongodb.reactivestreams.client.ChangeStreamPublisher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.producto.Producto
import models.producto.ProductoResponseError
import models.producto.ProductoResponseSuccess
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
        return productosRepository.findAll().flowOn(Dispatchers.IO)
    }

    fun watchProducto(): ChangeStreamPublisher<Producto> {
        logger.debug { "Cambios en producto" }
        return productoService.watch()
    }

    suspend fun createProducto(item: Producto): Producto {
        logger.debug { "Creando producto $item" }
        println(ProductoResponseSuccess(200, Json.encodeToString(item)))
        productosRepository.save(item)
        return item
    }

    suspend fun getProductoById(id: Id<Producto>): Producto? {
        logger.debug { "Obteniendo producto con id $id" }
        val producto = productosRepository.findByID(id)
        if (producto == null) System.err.println(ProductoResponseError(404, "NOT FOUND"))
        else println(ProductoResponseSuccess(200, Json.encodeToString(producto)))
        return producto
    }

    suspend fun updateProducto(item: Producto) {
        logger.debug { "Actualizando producto $item" }
        println(ProductoResponseSuccess(201, "Updated"))
        productosRepository.save(item)
    }

    suspend fun deleteProducto(item: Producto): Boolean {
        logger.debug { "Borrando producto $item" }
        println(ProductoResponseSuccess(200, "Deleted"))
        return productosRepository.delete(item)
    }
}