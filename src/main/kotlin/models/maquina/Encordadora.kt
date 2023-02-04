package models.maquina

import com.fasterxml.jackson.databind.ObjectMapper
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
) : Maquina(id, uuid, descripcion, fechaAdquisicion, numSerie, turno) {
    override fun toString(): String {
        return ObjectMapper().writeValueAsString(this)
    }
}
