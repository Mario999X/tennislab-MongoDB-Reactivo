package models

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
class Raqueta(
    @BsonId @Contextual
    val id: Id<Raqueta> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    val descripcion: String
)
{
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}