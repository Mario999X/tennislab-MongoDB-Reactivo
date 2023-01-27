package repositories.personalizar

import models.Personalizar
import org.litote.kmongo.Id
import repositories.CrudRepository

interface PersonalizarRepository : CrudRepository<Personalizar, Id<Personalizar>> {
}