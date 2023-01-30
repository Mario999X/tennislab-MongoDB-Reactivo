package dto

import kotlinx.serialization.Serializable
import models.maquina.Encordadora

@Serializable
data class EncordadoraDto(
    val id: String,
    val descripcion: String,
    val fechaAdquisicion: String,
    val numSerie: String,
    var isManual: String,
    var tensionMax: String,
    var tensionMin: String,
)

fun Encordadora.toEncordadoraDto(): EncordadoraDto {
    return EncordadoraDto(
        id.toString(),
        descripcion,
        fechaAdquisicion,
        numSerie.toString(),
        isManual.toString(),
        tensionMax.toString(),
        tensionMin.toString()
    )
}