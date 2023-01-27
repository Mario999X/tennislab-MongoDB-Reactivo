package di

import controllers.*
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
import repositories.tarea.TareasKtorFitRepository
import repositories.usuario.UsuariosCacheRepositoryImpl
import repositories.usuario.UsuariosKtorFitRepositoryImpl
import repositories.usuario.UsuariosMongoRepositoryImpl
import service.ProductoService

@Module
@ComponentScan("kotlin")
class DiModule

val myModule = module {
    //Clases Services
    single(named("ProductoService")) { ProductoService() }

    //KtorFit
    single(named("UsuariosKtorFit")) { UsuariosKtorFitRepositoryImpl() }
    single(named("TareasKtorFit")) { TareasKtorFitRepository() }


    //RepositoriesImplements
    single<ProductosRepository>(named("ProductosRepository")) { ProductosRepositoryImpl() }
    single<AdquisicionRepository>(named("AdquisicionRepository")) { AdquisicionRepositoryImpl() }
    single<EncordarRepository>(named("EncordarRepository")) { EncordarRepositoryImpl() }
    single<PersonalizarRepository>(named("PersonalizarRepository")) { PersonalizarRepositoryImpl() }
    single(named("UsuariosCacheRepository")) { UsuariosCacheRepositoryImpl() }
    single(named("UsuariosMongoRepository")) { UsuariosMongoRepositoryImpl() }


    //Controladores
    single { ProductoController(get(named("ProductosRepository")), get(named("ProductoService"))) }
    single { AdquisicionController(get(named("AdquisicionRepository"))) }
    single { EncordarController(get(named("EncordarRepository"))) }
    single { PersonalizarController(get(named("PersonalizarRepository"))) }
    single {
        APIController(
            get(named("UsuariosCacheRepository")),
            get(named("UsuariosMongoRepository")),
            get(named("UsuariosKtorFit")),
            get(named("TareasKtorFit"))
        )
    }
    single { TareaController(get(named("TareaRepository"))) }
}
