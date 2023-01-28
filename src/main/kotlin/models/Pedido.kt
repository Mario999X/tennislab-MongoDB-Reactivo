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
    val fechaEntrada: String,
    val fechaProgramada: String,
    var fechaSalida: String? = null, // esta es actualizable
    var cliente: Usuario,
    var tareas: List<Tarea>
){
    override fun toString(): String {
        return "Pedido(id=$id, uuid=$uuid, estadoPedido=$estadoPedido, fechaEntrada='$fechaEntrada', fechaProgramada='$fechaProgramada', fechaSalida=$fechaSalida, cliente=$cliente, tareas=$tareas)"
    }
}

enum class EstadoPedido { RECIBIDO, PROCESANDO, TERMINADO }