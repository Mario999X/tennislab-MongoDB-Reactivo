package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
class Producto(
    @BsonId @Contextual
    val id: Id<Producto> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    val tipo: Tipo,
    val descripcion: String, // marca + modelo
    val stock: Int,
    val precio: Double
) {
    override fun toString(): String {
        return "Producto(id=$id, uuid=$uuid, tipo=$tipo, descripcion='$descripcion', stock=$stock, precio=$precio)"
    }
}

enum class Tipo { RAQUETA, CORDAJE, COMPLEMENTO }


