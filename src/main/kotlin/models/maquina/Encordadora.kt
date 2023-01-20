package models.maquina

import org.litote.kmongo.Id
import java.util.UUID


class Encordadora(
    id: Id<Maquina>,
    uuid: UUID = UUID.randomUUID(),
    marca: String,
    modelo: String,
    fechaAdquisicion: String,
    numSerie: Long,
    var isManual: Boolean,
    var tensionMax: Double,
    var tensionMin: Double,
    // TURNO?
) : Maquina(id, uuid, marca, modelo, fechaAdquisicion, numSerie)
