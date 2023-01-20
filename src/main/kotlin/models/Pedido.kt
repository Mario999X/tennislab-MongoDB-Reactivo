package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
class Pedido(
    @BsonId @Contextual
    val id: Id<Pedido> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    var estadoPedido: EstadoPedido,
    var fechaEntrada: String,
    var fechaProgramada: String,
    var fechaSalida: String,
    // Cliente? yo creo que esto no tendria que estar a null, en los anteriores lo tenemos
// Tareas: MutableList
)

enum class EstadoPedido { RECIBIDO, PROCESANDO, TERMINADO }