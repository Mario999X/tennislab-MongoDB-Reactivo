package service.cache

import io.github.reactivecircus.cache4k.Cache
import models.Usuario
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.UUID
import kotlin.time.Duration.Companion.minutes

@Single
@Named("UsuariosCache")
class UsuariosCache {

    val refreshTime = 60000 // 1 minuto

    val cache = Cache.Builder()
        .expireAfterAccess(5.minutes)
        .build<UUID, Usuario>()
}