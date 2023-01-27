package repositories.usuario

import models.Usuario
import repositories.CrudRepository
import java.util.UUID

interface UsuarioRepository : CrudRepository<Usuario, UUID> {
}