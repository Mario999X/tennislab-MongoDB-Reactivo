package models

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
class Turno(
    @BsonId @Contextual
    val id: Id<Turno> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    var horario: TipoHorario,
    var trabajador: Usuario
)
{
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}

enum class TipoHorario { TEMPRANO, TARDE }