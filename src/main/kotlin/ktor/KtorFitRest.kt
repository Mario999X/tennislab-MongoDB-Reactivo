package ktor

import de.jensklingenberg.ktorfit.http.GET
import dto.UsuarioDto

interface KtorFitRest {

    @GET("users")
    suspend fun getAll(): List<UsuarioDto>

}