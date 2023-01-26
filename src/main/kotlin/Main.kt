import di.DiModule
import di.myModule
import mu.KotlinLogging
import org.koin.core.context.startKoin

private val logger = KotlinLogging.logger { }
fun main() {
    startKoin {
        printLogger()
        modules(
            DiModule().run { myModule }
        )
    }
    AppMongo().run()
}

