package models.maquina

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import models.Turno
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import java.util.*

@Serializable
open class Maquina(
    @BsonId @Contextual
    val id: Id<Maquina>,
    @Contextual
    val uuid: UUID,
    val descripcion: String,
    var fechaAdquisicion: String,
    var numSerie: Long,
    var turno: Turno?

) {
    override fun toString(): String {
        return "Maquina(id=$id, uuid=$uuid, descripcion='$descripcion', fechaAdquisicion='$fechaAdquisicion', numSerie=$numSerie, turno=$turno)"
    }
}