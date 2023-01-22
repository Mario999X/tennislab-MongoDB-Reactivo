package ktor


import dto.toUsuario
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Usuario
import service.cache.UsuariosCache


fun main(args: Array<String>): Unit = runBlocking {

    val repository = RepositoryApiUsers(UsuariosCache())

    // "Mapa normal"
    val mapa = mutableMapOf<Int, Usuario>()

    val select = launch {
        println("Obteniendo usuarios")
        repository.findAll().onStart { println("Comenzamos") }.onCompletion { println("Fin") }.collect {
            mapa[it.id.toInt()] = it.toUsuario("Hola1")
        }
    }

    select.join()

    mapa.forEach {
        println(it.value)
    }
}