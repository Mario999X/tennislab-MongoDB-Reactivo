package utils

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Clase de tipo Object que contiene el método para leer archivos Properties()
 */
object Propiedades {
    /**
     * Método genérico que lee propiedades de un archivo Properties()
     * @param String Nombre del archivo a leer
     * @return Devuelve el archivo especificado para obtener los datos deseados
     */
    fun propertiesReader(propertiesFile: String): Properties {
        val file =
            FileInputStream("." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + propertiesFile)
        val prop = Properties()
        prop.load(file)
        return prop
    }
}