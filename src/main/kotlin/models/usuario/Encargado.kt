package models.usuario

import org.litote.kmongo.Id
import java.util.*

class Encargado(
    id: Id<Usuario>,
    uuid: UUID,
    nombre: String,
    apellido: String,
    email: String,
    password: ByteArray,
    perfil: Perfil = Perfil.ADMIN,
    // Raqueta
) : Usuario(id, uuid, nombre, apellido, email, password)