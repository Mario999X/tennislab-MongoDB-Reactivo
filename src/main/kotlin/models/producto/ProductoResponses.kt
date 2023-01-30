package models.producto

import kotlinx.serialization.Serializable

@Serializable
sealed class ProductoResponse<Producto>

@Serializable
class ProductoResponseSuccess<T : Any>(val code: Int, val data: T) : ProductoResponse<T>()

@Serializable
class ProductoResponseError(val code: Int, val message: String) : ProductoResponse<String>()