package models.usuario

import org.litote.kmongo.Id
import java.util.UUID

class Cliente(
    id: Id<Usuario>,
    uuid: UUID,
    nombre: String,
    apellido: String,
    email: String,
    password: ByteArray,
    perfil: Perfil = Perfil.CLIENTE,
    // Raqueta?
) : Usuario(id, uuid, nombre, apellido, email, password)