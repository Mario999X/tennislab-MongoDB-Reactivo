package dto

import kotlinx.serialization.Serializable
import models.maquina.Personalizadora

@Serializable
data class PersonalizadoraDto(
    val descripcion: String,
    var maniobrabilidad: String,
    var balance: String,
    var rigidez: String
) {
}

fun Personalizadora.toPersonalizadoraDto(): PersonalizadoraDto {
    return PersonalizadoraDto(
        descripcion,
        maniobrabilidad.toString(),
        balance.toString(),
        rigidez.toString()
    )
}