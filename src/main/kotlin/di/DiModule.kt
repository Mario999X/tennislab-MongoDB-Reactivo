package di

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import controllers.*
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import repositories.adquisicion.AdquisicionRepository
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.encordar.EncordarRepository
import repositories.encordar.EncordarRepositoryImpl
import repositories.maquina.MaquinaEncordadoraRepository
import repositories.maquina.MaquinaEncordadoraRepositoryImpl
import repositories.maquina.MaquinaPersonalizadoraRepository
import repositories.maquina.MaquinaPersonalizadoraRepositoryImpl
import repositories.pedido.PedidoRepository
import repositories.pedido.PedidoRepositoryImpl
import repositories.personalizar.PersonalizarRepository
import repositories.personalizar.PersonalizarRepositoryImpl
import repositories.producto.ProductosRepository
import repositories.producto.ProductosRepositoryImpl
import repositories.tarea.TareasKtorFitRepository
import repositories.usuario.UsuariosCacheRepositoryImpl
import repositories.usuario.UsuariosKtorFitRepositoryImpl
import repositories.usuario.UsuariosMongoRepositoryImpl
import service.ProductoService

/**
 * Clase que contiene todas las anotaciones para la inyección de dependencias con Koin
 */
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
    single<PedidoRepository>(named("PedidoRepository")) { PedidoRepositoryImpl() }
    single<MaquinaEncordadoraRepository>(named("MaquinaEncordadoraRepository")) { MaquinaEncordadoraRepositoryImpl() }
    single<MaquinaPersonalizadoraRepository>(named("MaquinaPersonalizadoraRepository")) { MaquinaPersonalizadoraRepositoryImpl() }


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
    single { PedidoController(get(named("PedidoRepository"))) }
    single { MaquinaEncordadoraController(get(named("MaquinaEncordadoraRepository"))) }
    single { MaquinaPersonalizadoraController(get(named("MaquinaPersonalizadoraRepository"))) }
}
