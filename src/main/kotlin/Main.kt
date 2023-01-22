import di.myModule
import mu.KotlinLogging
import org.koin.core.context.startKoin

private val logger = KotlinLogging.logger { }
fun main() {
    startKoin {
        modules(myModule)
    }
    AppMongo().run()
}

