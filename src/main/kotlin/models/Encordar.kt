package models

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
class Encordar(
    @BsonId @Contextual
    val id: Id<Encordar> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    var informacionEndordado: String,
    val precio: Double = 15.0
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}