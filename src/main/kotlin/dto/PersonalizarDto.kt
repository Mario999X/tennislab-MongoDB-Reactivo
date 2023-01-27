package dto

import kotlinx.serialization.Serializable
import models.Personalizar

@Serializable
data class PersonalizarDto(
    val id: String,
    val uuid: String,
    val informacionPersonalizacion: String,
    val precio: String
) {

}

fun Personalizar.toPersonalizarDto(): PersonalizarDto {
    return PersonalizarDto(
        id = id.toString(),
        uuid = uuid.toString(),
        informacionPersonalizacion,
        precio = precio.toString()
    )
}
