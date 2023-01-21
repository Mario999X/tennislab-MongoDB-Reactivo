package utils

import models.Usuario
import org.litote.kmongo.Id
import org.litote.kmongo.id.StringId

object TransformIDs {

    fun generateIdUsers(id: String): Id<Usuario> {
        return StringId(id)
    }
}