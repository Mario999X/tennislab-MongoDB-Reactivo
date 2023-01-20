package models.maquina

import org.litote.kmongo.Id
import java.util.*

class Personalizadora(
    id: Id<Maquina>,
    uuid: UUID = UUID.randomUUID(),
    descripcion: String,
    fechaAdquisicion: String,
    numSerie: Long,
    var manioabrilidad: Boolean,
    var balance: Boolean,
    var rigidez: Boolean
    // TURNO?
) : Maquina(id, uuid, descripcion, fechaAdquisicion, numSerie)
