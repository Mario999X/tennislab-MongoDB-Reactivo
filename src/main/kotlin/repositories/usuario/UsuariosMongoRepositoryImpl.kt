package repositories.usuario

/**
 * @author Mario Resa y Sebastián Mendoza
 */
import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import models.Usuario
import org.litote.kmongo.Id

/**
 * Repositorios de "Usuarios", encargados de manejar operaciones CRUD basicas
 *
 */
class UsuariosMongoRepositoryImpl : UsuarioRepository {

    override fun findAll(): Flow<Usuario> {
        println("\tfindAllMongo")
        return MongoDbManager.database.getCollection<Usuario>().find().toFlow()
    }

    override suspend fun findByID(id: Id<Usuario>): Usuario? {
        println("\tfindByIDMongo")
        return MongoDbManager.database.getCollection<Usuario>().findOneById(id)
    }

    override suspend fun delete(entity: Usuario): Boolean {
        println("\tdeleteMongo")
        var existe = false
        if (MongoDbManager.database.getCollection<Usuario>().deleteOneById(entity.id).equals(entity)) existe = true

        return existe
    }

    override suspend fun save(entity: Usuario): Usuario {
        println("\tsaveMongo")
        MongoDbManager.database.getCollection<Usuario>().save(entity)
        return entity
    }

}