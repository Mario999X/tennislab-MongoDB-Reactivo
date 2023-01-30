package models.maquina

import models.Turno
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.UUID


class Encordadora(
    id: Id<Maquina> = newId(),
    uuid: UUID = UUID.randomUUID(),
    descripcion: String,
    fechaAdquisicion: String,
    numSerie: Long,
    turno: Turno? = null,
    var isManual: Boolean,
    var tensionMax: Double,
    var tensionMin: Double,

    // TURNO?
) : Maquina(id, uuid, descripcion, fechaAdquisicion, numSerie, turno) {
    override fun toString(): String {
        return "Encordadora(isManual=$isManual, tensionMax=$tensionMax, tensionMin=$tensionMin)"
    }
}
