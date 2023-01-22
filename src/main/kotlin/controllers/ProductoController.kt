package controllers

import com.mongodb.reactivestreams.client.ChangeStreamPublisher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import models.Producto
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.litote.kmongo.Id
import repositories.producto.ProductosRepository
import service.ProductoService

private val logger = KotlinLogging.logger { }

@Single
@Named("productoController")
class ProductoController(
    @Named("Productosrepository") private val productosRepository: ProductosRepository,
    private val productoService: ProductoService
) {
    suspend fun getProductos(): Flow<Producto> {
        logger.debug { "Obteniendo productos" }
        return productosRepository.findAll().flowOn(Dispatchers.IO)
    }

    fun watchProducto(): ChangeStreamPublisher<Producto> {
        logger.debug { "Cambios en producto" }
        return productoService.watch()
    }

    suspend fun createProducto(item: Producto): Producto {
        logger.debug { "Creando producto $item" }
        productosRepository.save(item)
        return item
    }

    suspend fun getProductoById(id: Id<Producto>): Producto? {
        logger.debug { "Obteniendo producto con id $id" }
        return productosRepository.findByID(id)
    }

    suspend fun updateProducto(item: Producto) {
        logger.debug { "Actualizando producto $item" }
        productosRepository.save(item)
    }

    suspend fun deleteProducto(item: Producto): Boolean {
        logger.debug { "Borrando producto $item" }
        return productosRepository.delete(item)
    }
}