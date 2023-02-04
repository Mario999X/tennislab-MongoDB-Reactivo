package dto

import kotlinx.serialization.Serializable
import models.Encordar

@Serializable
data class EncordarDto(
    val informacionEncordado: String,
    val precio: String
) {
}

fun Encordar.toEncordarDto(): EncordarDto {
    return EncordarDto(
        informacionEndordado,
        precio = precio.toString()
    )
}