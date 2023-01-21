import controllers.ProductoController
import db.MongoDbManager
import db.getProductoInit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import models.Producto
import mu.KotlinLogging
import repositories.ProductosRepositoryImpl
import service.ProductoService

private val logger = KotlinLogging.logger { }
fun main(): Unit = runBlocking {
    val limpiar = launch {
        limpiarDatos()
    }
    limpiar.join()

    val controller = ProductoController(ProductosRepositoryImpl(), ProductoService())
    val productosList = mutableListOf<Producto>()

    val escuchadorProducto = launch {
        println("Escuchando cambios en producto")
        controller.watchProducto().collect {
            println("Evento: ${it.operationType.value} -> ${it.fullDocument}")
        }
    }

    val init = launch {
        val productosInit = getProductoInit()
        productosInit.forEach { producto ->
            controller.createProducto(producto)
        }

        productosList.clear()
        controller.getProductos().collect { producto ->
            productosList.add(producto)
        }

        productosList.forEach { producto ->
            println(producto)
        }
    }
    init.join()

}

suspend fun limpiarDatos() = withContext(Dispatchers.IO) {
    logger.debug { "Borrando datos de la base de datos" }
    MongoDbManager.database.getCollection<Producto>().drop()
}