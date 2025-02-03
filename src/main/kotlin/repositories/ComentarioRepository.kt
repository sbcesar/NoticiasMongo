package org.example.repositories

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import org.bson.Document
import org.bson.types.ObjectId
import org.example.config.DatabaseManager
import org.example.entity.Comentario

class ComentarioRepository(private val databaseManager: DatabaseManager) {

    private val collName = "comentarios"

    fun listarComentariosPorNoticia(noticiaId: ObjectId): List<String> {
        val comentarioCollection = databaseManager.getCollection(collName)

        val pipeline = listOf(
            Aggregates.match(Filters.eq("noticia_id", noticiaId)),

            Aggregates.lookup(
                "usuarios",
                "usuario_id",
                "_id",
                "usuario_info"
            )
        )

        val resultado = comentarioCollection.aggregate(pipeline)

        val comentarios = mutableListOf<String>()

        resultado.forEach { document ->
            comentarios.add(document.toJson())
        }

        return comentarios
    }

    fun agregarComentario(comentario: Comentario): String {
        val comentarioCollection = databaseManager.getCollection(collName)

        val comentarioDoc = Document()
            .append("usuario_id", comentario.usuario._id)
            .append("noticia_id", comentario.noticia._id)
            .append("comentario", comentario.comentario)
            .append("fecha", comentario.fecha)

        comentarioCollection.insertOne(comentarioDoc)
        return "Comentario agregado"
    }
}