package di

import controllers.AdquisicionController
import controllers.EncordarController
import controllers.PersonalizarController
import controllers.ProductoController
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import repositories.adquisicion.AdquisicionRepository
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.encordar.EncordarRepository
import repositories.encordar.EncordarRepositoryImpl
import repositories.personalizar.PersonalizarRepository
import repositories.personalizar.PersonalizarRepositoryImpl
import repositories.producto.ProductosRepository
import repositories.producto.ProductosRepositoryImpl
import service.ProductoService

@Module
@ComponentScan("kotlin")
class DiModule

val myModule = module {
    //RepositoriesImplements
    single<ProductosRepository>(named("ProductosRepository")) { ProductosRepositoryImpl() }
    single<AdquisicionRepository>(named("AdquisicionRepository")) { AdquisicionRepositoryImpl() }
    single<EncordarRepository>(named("EncordarRepository")) { EncordarRepositoryImpl() }
    single<PersonalizarRepository>(named("PersonalizarRepository")) { PersonalizarRepositoryImpl() }

    //Clases Services
    single(named("ProductoService")) { ProductoService() }

    //Controladores
    single { ProductoController(get(named("ProductosRepository")), get(named("ProductoService"))) }
    single { AdquisicionController(get(named("AdquisicionRepository"))) }
    single { EncordarController(get(named("EncordarRepository"))) }
    single { PersonalizarController(get(named("PersonalizarRepository"))) }

}
