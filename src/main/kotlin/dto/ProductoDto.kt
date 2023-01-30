package dto

import kotlinx.serialization.Serializable
import models.Producto

@Serializable
data class ProductoDto(
    val id: String,
    val tipo: String,
    val descripcion: String,
    val stock: String,
    val precio: String
) {
}

fun Producto.toProductoDto(): ProductoDto {
    return ProductoDto(
        id = id.toString(),
        tipo = tipo.name,
        descripcion,
        stock = stock.toString(),
        precio = precio.toString()
    )
}