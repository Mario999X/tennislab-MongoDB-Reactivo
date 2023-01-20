package models

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
    var informacionEndordado: String, // Aqui ponemos el texto que haga falta
    val precio: Double = 15.0 // Necesitamos el precio individual
// Tarea
)