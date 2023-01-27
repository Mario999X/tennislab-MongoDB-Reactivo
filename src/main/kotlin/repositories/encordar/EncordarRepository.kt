package repositories.encordar

import models.Encordar
import org.litote.kmongo.Id
import repositories.CrudRepository

interface EncordarRepository : CrudRepository<Encordar, Id<Encordar>> {
}