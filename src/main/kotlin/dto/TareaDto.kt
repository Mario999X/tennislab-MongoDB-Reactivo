package dto

import kotlinx.serialization.Serializable
import models.Tarea

@Serializable
data class TareaDto(
    val id: String,
    val uuid: String,
    val adquisicionDto: AdquisicionDto?,
    val personalizarDto: PersonalizarDto?,
    val encordarDto: EncordarDto?,
    val precio: String
) {

}

fun Tarea.toTareaDto(): TareaDto {
    return TareaDto(
        id = id.toString(),
        uuid = uuid.toString(),
        adquisicionDto = adquisicion?.toAdquisicionDto(),
        personalizarDto = personalizar?.toPersonalizarDto(),
        encordarDto = encordar?.toEncordarDto(),
        precio = precio.toString()
    )
}
