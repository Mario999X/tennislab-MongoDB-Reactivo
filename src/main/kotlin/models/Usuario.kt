package models

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

@Serializable
class Usuario(
    @BsonId @Contextual
    val id: Id<Usuario> = newId(),
    @Contextual
    val uuid: UUID = UUID.randomUUID(),
    var name: String,
    var email: String,
    val password: ByteArray,
    var raqueta: List<Raqueta>? = null,
    var perfil: Perfil
) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}

enum class Perfil { ADMIN, ENCORDADOR, CLIENTE }