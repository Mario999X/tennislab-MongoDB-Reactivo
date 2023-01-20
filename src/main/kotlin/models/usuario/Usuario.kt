package models.usuario

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import java.util.*

@Serializable
open class Usuario(
    @BsonId @Contextual
    val id: Id<Usuario>,
    @Contextual
    val uuid: UUID,
    var nombre: String,
    var apellido: String,
    var email: String,
    val password: ByteArray
)

enum class Perfil { ADMIN, ENCORDADOR, CLIENTE }