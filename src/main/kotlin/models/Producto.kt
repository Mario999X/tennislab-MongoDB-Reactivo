package models

import com.fasterxml.jackson.databind.ObjectMapper
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
    val descripcion: String,
    val stock: Int,
    var precio: Double
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}

enum class Tipo { RAQUETA, CORDAJE, COMPLEMENTO }


