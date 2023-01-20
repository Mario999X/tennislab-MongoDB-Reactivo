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
    val descripcion: String, // marca + modelo
    var fechaAdquisicion: String,
    var numSerie: Long
)