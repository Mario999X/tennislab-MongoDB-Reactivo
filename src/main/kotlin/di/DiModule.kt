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

val myModule = module {
    single<ProductosRepository> { ProductosRepositoryImpl() }
    single<AdquisicionRepository> { AdquisicionRepositoryImpl() }

    single(named("ProductoController")) { ProductoController(get(), get()) }
    single(named("AdquisicionController")) { AdquisicionController(get(), get()) }
}
