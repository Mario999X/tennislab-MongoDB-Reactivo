package dto

import kotlinx.serialization.Serializable
import models.maquina.Personalizadora

@Serializable
data class PersonalizadoraDto(
    val id: String,
    val descripcion: String,
    val fechaAdquisicion: String,
    val numSerie: String,
    var maniobrabilidad: String,
    var balance: String,
    var rigidez: String
) {
}

fun Personalizadora.toPersonalizadoraDto(): PersonalizadoraDto {
    return PersonalizadoraDto(
        id.toString(),
        descripcion,
        fechaAdquisicion,
        numSerie.toString(),
        maniobrabilidad.toString(),
        balance.toString(),
        rigidez.toString()
    )
}