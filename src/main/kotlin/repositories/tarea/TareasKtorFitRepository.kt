package repositories.tarea

import dto.toAdquisicionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ktorfit.KtorFitClient
import models.Adquisicion


class TareasKtorFitRepository {
    // Inyectar dependencia
    private val client by lazy { KtorFitClient.instance }

    suspend fun uploadAdquisicion(entity: Adquisicion): Adquisicion = withContext(Dispatchers.IO) {
        println("\tuploadAdquisicion")
        client.createAdquision(entity.toAdquisicionDto())
        return@withContext entity
    }
}