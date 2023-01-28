package db

import models.*

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

fun getAdquisicionInit() = listOf(
    Adquisicion(
        cantidad = 1,
        producto = getProductoInit()[0],
    ),
    Adquisicion(
        cantidad = 2,
        producto = getProductoInit()[1],
    ),
)

fun getEncordaciones() = listOf(
    Encordar(
        informacionEndordado = "HOLA"
    ),
    Encordar(
        informacionEndordado = "ADIOS"
    )
)

fun getPersonalizaciones() = listOf(
    Personalizar(
        informacionPersonalizacion = "HOLA"
    ),
    Personalizar(
        informacionPersonalizacion = "ADIOS"
    )
)

