package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
class Tarea(
    @BsonId @Contextual
    val id: Id<Tarea> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    // Adquision?
// Personalizar?
// Encordar?
var precio: Double = 0.0 // TODO revisar precio, ahora si

// Trabajador?
// Pedido?
)