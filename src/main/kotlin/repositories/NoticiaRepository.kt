package org.example.repositories

import com.mongodb.client.MongoDatabase
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
import java.util.*

class NoticiaRepository(private val database: MongoDatabase) {

    private var collName = "noticias"

    fun publicarNoticia(noticia: Noticia): Noticia {
        val noticiaColeccion = database.getCollection(collName, Noticia::class.java)

        noticiaColeccion.insertOne(noticia)

        return noticia
    }

    fun listarNoticiasPorUsuario(usuarioNick: String): List<Noticia> {
        val noticiaColeccion = database.getCollection(collName, Noticia::class.java)

        val filtro = Filters.eq("autor.apodo", usuarioNick)

        val noticias = noticiaColeccion.find(filtro).toList()

        return noticias.ifEmpty {
            emptyList()
        }
    }

    fun buscarNoticiasPorEtiquetas(etiquetas: List<String>): List<Noticia> {
        val noticiasCollection = database.getCollection(collName, Noticia::class.java)

        val filtro = Filters.all("tag", etiquetas)

        val noticias = noticiasCollection.find(filtro).toList()

        return noticias.ifEmpty {
            emptyList()
        }
    }

    fun buscarNoticiaPorTitulo(titulo: String): List<Noticia> {
        val noticiasCollection = database.getCollection(collName, Noticia::class.java)

        val filtro = Filters.eq("titulo", titulo)

        val noticias = noticiasCollection.find(filtro).toList()

        return noticias.ifEmpty {
            emptyList()
        }
    }

    fun listarTodas(): List<Noticia> {
        val noticiasCollection = database.getCollection(collName, Noticia::class.java)

        return noticiasCollection.find().toList()
    }

    fun listarUltimasNoticias(): List<Noticia> {
        val noticiasCollection = database.getCollection(collName, Noticia::class.java)

        val noticias = noticiasCollection.find()
            .sort(Sorts.descending("fecha_publicacion"))
            .limit(10)
            .toList()

        return noticias
    }

}