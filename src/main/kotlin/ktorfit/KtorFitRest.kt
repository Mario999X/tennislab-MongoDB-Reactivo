package ktorfit

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import dto.AdquisicionDto
import dto.UsuarioDto

interface KtorFitRest {

    @GET("users")
    suspend fun getAll(): List<UsuarioDto>

    @POST("todos")
    suspend fun createAdquision(@Body adquisicion: AdquisicionDto): AdquisicionDto

}