package repositories.usuario

import db.MongoDbManager
import kotlinx.coroutines.flow.Flow
import models.Usuario
import java.util.*

class UsuariosMongoRepositoryImpl : UsuarioRepository {

    override fun findAll(): Flow<Usuario> {
        println("\tfindAllMongo")
        return MongoDbManager.database.getCollection<Usuario>().find().toFlow()
    }

    override suspend fun findByID(id: UUID): Usuario? {
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