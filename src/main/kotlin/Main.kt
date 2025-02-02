package org.example

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.example.config.DatabaseManager
import org.example.entity.Cliente
import org.example.entity.Direccion
import org.example.entity.Estado
import org.example.repositories.UsuarioRepository
import org.example.utils.Menu

fun main() {

    val databaseManager = DatabaseManager()
    val menu = Menu()
    val usuarioRepository = UsuarioRepository(databaseManager)

    try {
        databaseManager.connect()
        // menu.showMenu()
        val direccion = Direccion("hola", "hola", "hola", "hola")
        usuarioRepository.register("cesar@gmail.com", "cesar", "cemaik", Estado.INACTIVO, direccion, listOf("717707815"))
    }catch (e: Exception){
        println(e.message)
    } finally {
        databaseManager.disconnect()
    }

//    // Abrir la conexión con la BD
//    val database = ConexionMongo.getDatabase("adaprueba")
//
//    // Obtener la colección
//    val collection: MongoCollection<Cliente> = database.getCollection("collboticias\n", Cliente::class.java)
//
//    try {
//        // Declarar un cliente y una direccion
//        val direccion = Direccion("alamo", "24", "04638", "Mojacar")
//        val cliente = Cliente("maria@gmail.com", "Maria", "mar14", true, listOf("950475656", "666888999"), direccion)
//
//        collection.insertOne(cliente)
//
//        val direccion2 = Direccion("desconocida", "24", "04003", "Almeria")
//        val direccion3 = Direccion("principal", "2", "04003", "Almeria")
//        val direccion4 = Direccion("principal", "1", "04003", "Almeria")
//
//        val cliente2 = Cliente("pedro@gmail.com", "Pedro", "periko", true, listOf("950475656", "666888999"), direccion2)
//        val cliente3 = Cliente("ana@gmail.com", "Ana", "anuski", true, listOf("950475656", "666888999"), direccion3)
//        val cliente4 = Cliente("antonio@gmail.com", "Antonio", "toni", true, listOf("950475658", "666888999"), direccion4)
//        val cliente5 = Cliente("agustin@gmail.com", "Agustin", "agus", true, listOf("950475656", "666888999"), direccion4)
//
//        val listaClientes = listOf<Cliente>(
//            cliente2, cliente3, cliente4, cliente5
//        )
//
//        collection.insertMany(listaClientes)
//
//
//
//    } catch (e: Exception) {
//        println("Clave duplicada")
//    }
//
//    // CONSULTA DIRECCION
//    val filtro = Filters.eq("direccion.cp", "04638")
//
//    collection.find(filtro).forEach { println(it) }
//
//    // CONSULTA TELEFONO
//    val filtro2 = Filters.eq("telefonos", "950475658")
//    collection.find(filtro2).forEach { println(it) }
//
//    // CONSULTAR COUNT
//    val filtroCount = Filters.eq("direccion.cp","04638")
//    println(collection.find(filtroCount).count())
//    println()
//    ConexionMongo.close()
}