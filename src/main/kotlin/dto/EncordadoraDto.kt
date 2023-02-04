package dto

import kotlinx.serialization.Serializable
import models.maquina.Encordadora

@Serializable
data class EncordadoraDto(
    val descripcion: String,
    var isManual: String,
    var tensionMax: String,
    var tensionMin: String,
)

fun Encordadora.toEncordadoraDto(): EncordadoraDto {
    return EncordadoraDto(
        descripcion,
        isManual.toString(),
        tensionMax.toString(),
        tensionMin.toString()
    )
}