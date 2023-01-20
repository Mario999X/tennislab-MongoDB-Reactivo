package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

// Embebido?
@Serializable
class Raqueta(
    @BsonId @Contextual
    val id: Id<Raqueta> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    var marca: String,
    var modelo: String
)