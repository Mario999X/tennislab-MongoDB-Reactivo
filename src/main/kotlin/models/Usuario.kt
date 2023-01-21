package models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import java.util.*

@Serializable
class Usuario(
    @BsonId @Contextual
    val id: Id<Usuario>,
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    var name: String,
    var email: String,
    val password: ByteArray,
    val perfil: Perfil
) {
    override fun toString(): String {
        return "Usuario(id=$id, uuid=$uuid, name='$name', email='$email', password=$password, perfil=$perfil)"
    }
}

enum class Perfil { ADMIN, ENCORDADOR, CLIENTE }