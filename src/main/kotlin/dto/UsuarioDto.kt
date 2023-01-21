package dto

import kotlinx.serialization.Serializable
import models.Perfil
import models.Usuario
import utils.Cifrador
import utils.TransformIDs
import java.util.*

@Serializable
data class UsuarioDto(
    val id: String,
    var name: String,
    var email: String,
) {
}

fun UsuarioDto.toUsuario(pass: String): Usuario {
    return Usuario(
        id = TransformIDs.generateIdUsers(id),
        uuid = UUID.randomUUID(),
        name = name,
        email = email,
        password = Cifrador.codifyPassword(pass),
        perfil = Perfil.CLIENTE
    )
}