package di

import controllers.AdquisicionController
import controllers.ProductoController
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import repositories.adquisicion.AdquisicionRepository
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.producto.ProductosRepository
import repositories.producto.ProductosRepositoryImpl
import service.AdquisicionService
import service.ProductoService

@Module
@ComponentScan("kotlin")
class DiModule

val myModule = module {
    //RepositorisImplements
    single<ProductosRepository>(named("ProductosRepository")) { ProductosRepositoryImpl() }
    single<AdquisicionRepository>(named("AdquisicionRepository")) { AdquisicionRepositoryImpl() }

    //Clases Services
    single(named("ProductoService")) { ProductoService() }
    single(named("AdquisicionService")) { AdquisicionService() }

    //Controladores
    single { ProductoController(get(named("ProductosRepository")), get(named("ProductoService"))) }
    single { AdquisicionController(get(named("AdquisicionRepository")), get(named("AdquisicionService"))) }
}
