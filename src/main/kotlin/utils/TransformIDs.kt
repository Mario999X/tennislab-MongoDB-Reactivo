package utils

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import models.Usuario
import org.litote.kmongo.Id
import org.litote.kmongo.id.StringId

/**
 * Clase Object que contiene una función que genera ID's del tipo MongoDB
 */
object TransformIDs {

    fun generateIdUsers(id: String): Id<Usuario> {
        return StringId(id)
    }
}