package models

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
class Personalizar(
    @BsonId @Contextual
    val id: Id<Personalizar> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    var informacionPersonalizacion: String,
    val precio: Double = 60.0
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}
