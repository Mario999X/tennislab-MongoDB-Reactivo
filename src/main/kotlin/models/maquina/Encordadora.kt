package models.maquina

import org.litote.kmongo.Id
import java.util.UUID


class Encordadora(
    id: Id<Maquina>,
    uuid: UUID = UUID.randomUUID(),
    descripcion: String,
    fechaAdquisicion: String,
    numSerie: Long,
    var isManual: Boolean,
    var tensionMax: Double,
    var tensionMin: Double,
    // TURNO?
) : Maquina(id, uuid, descripcion, fechaAdquisicion, numSerie)
