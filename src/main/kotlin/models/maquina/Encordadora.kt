package models.maquina

import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.UUID


class Encordadora(
    id: Id<Maquina> = newId(),
    uuid: UUID = UUID.randomUUID(),
    descripcion: String,
    fechaAdquisicion: String,
    numSerie: Long,
    var isManual: Boolean,
    var tensionMax: Double,
    var tensionMin: Double,

    // TURNO?
) : Maquina(id, uuid, descripcion, fechaAdquisicion, numSerie) {
    override fun toString(): String {
        return "Encordadora(id=$id, uuid=$uuid, descripcion='$descripcion', fechaAdquisicion='$fechaAdquisicion', numSerie=$numSerie,isManual=$isManual, tensionMax=$tensionMax, tensionMin=$tensionMin)"
    }
}
