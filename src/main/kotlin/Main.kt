/**
 * @author Mario Resa y Sebastián Mendoza
 */
import di.DiModule
import di.myModule
import org.koin.core.context.startKoin

/**
 * Main principal al que se le implementa Koin para poder utilizar las inyecciones de dependencias puestas en el proyecto
 */
fun main() {
    startKoin {
        printLogger()
        modules(
            DiModule().run { myModule }
        )
    }
    KtorFitApp().run()
    AppMongo().run()
}

