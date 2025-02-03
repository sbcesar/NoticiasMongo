package org.example.repositories

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.example.entity.Comentario
import org.example.entity.Noticia

class ComentarioRepository(private val database: MongoDatabase) {

    private val collName = "comentarios"

    fun listarComentariosPorNoticia(noticia: Noticia): List<Comentario> {
        val comentarioCollection = database.getCollection(collName, Comentario::class.java)

        val filtro = Filters.eq("noticia_id", noticia._id)

        val comentarios = comentarioCollection.find(filtro).toList()

        return comentarios.ifEmpty {
            emptyList()
        }
    }

    fun agregarComentario(comentario: Comentario): Comentario {
        val comentarioCollection = database.getCollection(collName, Comentario::class.java)

        comentarioCollection.insertOne(comentario)

        return comentario
    }
}