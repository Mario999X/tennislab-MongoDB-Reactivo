package models

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

enum class TipoHorario { TEMPRANO, TARDE }