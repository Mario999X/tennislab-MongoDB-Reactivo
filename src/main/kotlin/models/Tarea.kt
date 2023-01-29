package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import utils.CalculoPrecioTarea
import java.util.*

@Serializable
class Tarea(
    @BsonId @Contextual
    val id: Id<Tarea> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    val adquisicion: Adquisicion? = null,
    val personalizar: Personalizar? = null,
    val encordar: Encordar? = null,
    var precio: Double = CalculoPrecioTarea.calculatePrecio(
        adquisicion?.precio,
        personalizar?.precio,
        encordar?.precio
    ),
    var usuario: Usuario, // En el controlador se maneja que solo pueda ser de tipo ENCORDADOR
    var raqueta: Raqueta? = null
) {
    override fun toString(): String {
        return "Tarea(id=$id, uuid=$uuid, adquisicion=$adquisicion, personalizar=$personalizar, encordar=$encordar, precio=$precio, usuario=$usuario, raqueta=$raqueta)"
    }
}