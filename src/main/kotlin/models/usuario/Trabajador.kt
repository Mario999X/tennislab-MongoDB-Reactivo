package models.usuario

import org.litote.kmongo.Id
import java.util.*

class Trabajador(
    id: Id<Usuario>,
    uuid: UUID,
    nombre: String,
    apellido: String,
    email: String,
    password: ByteArray,
    perfil: Perfil = Perfil.ENCORDADOR,
    // Turno?
// Tarea?
) : Usuario(id, uuid, nombre, apellido, email, password)