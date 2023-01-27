import di.DiModule
import di.myModule
import ktorfit.KtorFitApp
import org.koin.core.context.startKoin

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

