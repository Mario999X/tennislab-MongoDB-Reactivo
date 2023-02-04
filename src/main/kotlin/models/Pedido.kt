package models

import com.fasterxml.jackson.databind.ObjectMapper
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
    var fechaSalida: String? = null,
    var cliente: Usuario,
    var tareas: List<Tarea>,
    val precio: Double = tareas.sumOf { it.precio }
){
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}

enum class EstadoPedido { RECIBIDO, PROCESANDO, TERMINADO }