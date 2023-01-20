package db

import models.Producto
import models.Tipo

fun getProductoInit() = listOf(
    Producto(
        tipo = Tipo.RAQUETA,
        descripcion = "Babolat Pure Air",
        stock = 3,
        precio = 279.95
    ),
    Producto(
        tipo = Tipo.COMPLEMENTO,
        descripcion = "Wilson Dazzle",
        stock = 5,
        precio = 7.90
    )
)