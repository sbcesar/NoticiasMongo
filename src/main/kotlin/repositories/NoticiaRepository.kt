package org.example.repositories

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import org.bson.Document
import org.bson.types.ObjectId
import org.example.config.DatabaseManager
import org.example.entity.Direccion
import org.example.entity.Estado
import org.example.entity.Noticia
import org.example.entity.Usuario

class NoticiaRepository(private val databaseManager: DatabaseManager) {

    private var collName = "noticias"

    fun publicarNoticia(noticia: Noticia): String {
        val noticiaColeccion = databaseManager.getCollection(collName)

        val noticiaDoc = Document()
            .append("titulo", noticia.titulo)
            .append("cuerpo", noticia.cuerpo)
            .append("fecha_publicacion", noticia.fech_publicacion)
            .append("autor_id", noticia.autor._id)
            .append("tags", noticia.tag)

        noticiaColeccion.insertOne(noticiaDoc)
        return "Noticia publicada exitosamente"
    }

    fun listarNoticiasPorUsuario(usuarioNick: String): List<String> {
        val noticiaColeccion = databaseManager.getCollection(collName)

        val pipeline = listOf(
            Aggregates.lookup(
                "usuarios",
                "autor_id",
                "_id",
                "usuario_info"
            ),
            Aggregates.match(Filters.eq("usuario_info", usuarioNick))
        )

        val resultado = noticiaColeccion.aggregate(pipeline)

        val noticias = mutableListOf<String>()

        resultado.forEach { document ->

            noticias.add(document.toJson())
        }
        return noticias
    }

    fun buscarNoticiasPorEtiquetas(etiquetas: List<String>): List<String> {
        val noticiasCollection = databaseManager.getCollection(collName)

        val pipeline = listOf(
            Aggregates.match(Filters.`in`("tags", etiquetas))
        )

        val resultado = noticiasCollection.aggregate(pipeline)

        val noticias = mutableListOf<String>()

        resultado.forEach { document ->
            noticias.add(document.toJson())
        }

        return noticias
    }

    fun listarUltimasNoticias(): List<String> {
        val noticiasCollection = databaseManager.getCollection(collName)

        val pipeline = listOf(
            Aggregates.sort(Sorts.descending("fecha_publicacion")),
            Aggregates.limit(10)
        )

        val resultado = noticiasCollection.aggregate(pipeline)

        val noticias = mutableListOf<String>()

        resultado.forEach { document ->
            noticias.add(document.toJson())
        }

        return noticias
    }

}