package models.maquina

import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

class Personalizadora(
    id: Id<Maquina> = newId(),
    uuid: UUID = UUID.randomUUID(),
    descripcion: String,
    fechaAdquisicion: String,
    numSerie: Long,
    var maniobrabilidad: Boolean,
    var balance: Boolean,
    var rigidez: Boolean
    // TURNO?
) : Maquina(id, uuid, descripcion, fechaAdquisicion, numSerie) {
    override fun toString(): String {
        return "Personalizadora(id=$id, uuid=$uuid, descripcion='$descripcion', fechaAdquisicion='$fechaAdquisicion', numSerie=$numSerie, manioabrilidad=$maniobrabilidad, balance=$balance, rigidez=$rigidez)"
    }
}
