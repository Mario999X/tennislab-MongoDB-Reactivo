package models.maquina

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import java.util.*

@Serializable
open class Maquina(
    @BsonId @Contextual
    val id: Id<Maquina>,
    @Contextual
    val uuid: UUID,
    var marca: String,
    var modelo: String,
    var fechaAdquisicion: String,
    var numSerie: Long
)