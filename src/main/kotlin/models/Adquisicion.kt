package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
class Adquisicion(
    @BsonId @Contextual
    val id: Id<Adquisicion> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    var cantidad: Int,
    val producto: Producto,
    val precio: Double = producto.precio * cantidad

// Tarea?
) {
    override fun toString(): String {
        return "Adquisicion(id=$id, uuid=$uuid, cantidad=$cantidad, producto=$producto, precio=$precio)"
    }
}