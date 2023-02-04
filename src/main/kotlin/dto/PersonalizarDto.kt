package dto

import kotlinx.serialization.Serializable
import models.Personalizar

@Serializable
data class PersonalizarDto(
    val informacionPersonalizacion: String,
    val precio: String
) {

}

fun Personalizar.toPersonalizarDto(): PersonalizarDto {
    return PersonalizarDto(
        informacionPersonalizacion,
        precio = precio.toString()
    )
}
