package dto

import kotlinx.serialization.Serializable
import models.Encordar

@Serializable
data class EncordarDto(
    val id: String,
    val uuid: String,
    val informacionEncordado: String,
    val precio: String
) {
}

fun Encordar.toEncordarDto(): EncordarDto {
    return EncordarDto(
        id = id.toString(),
        uuid = uuid.toString(),
        informacionEndordado,
        precio = precio.toString()
    )
}