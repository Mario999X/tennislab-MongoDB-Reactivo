package di

import controllers.ProductoController
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.dsl.single
import repositories.ProductosRepository
import repositories.ProductosRepositoryImpl
import service.ProductoService

@Module
@ComponentScan("kotlin")
class AppModule

val myModule = module {
    single<ProductosRepository> { ProductosRepositoryImpl() }

    single(named("ProductoController")) { ProductoController(get(), get()) }
}
