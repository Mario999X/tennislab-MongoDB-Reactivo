package repositories.adquisicion

import models.Adquisicion
import org.litote.kmongo.Id
import repositories.CrudRepository

interface AdquisicionRepository : CrudRepository<Adquisicion, Id<Adquisicion>>