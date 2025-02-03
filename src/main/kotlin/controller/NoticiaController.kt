package org.example.controller

import org.example.entity.Noticia
import org.example.entity.Usuario
import org.example.services.NoticiaService
import java.time.LocalDate
import java.util.*

class NoticiaController(private val noticiaService: NoticiaService) {

    fun publicarNoticia(noticia: Noticia): Noticia {
        if (noticia.titulo.isBlank() || noticia.cuerpo.isBlank()) {
            throw Exception("El título y contenido son obligatorios")
        }

        val fechaActual = Date()
        if (noticia.fech_publicacion.before(fechaActual)) {
            throw Exception("La fecha no puede ser anterior a la actual")
        }

        if (noticia.tag.isEmpty()) {
            throw Exception("Los tags son obligatorios")
        }

        return noticiaService.publicarNoticia(noticia)
    }

    fun listarNoticiasPorUsuario(usuarioNick: String): List<Noticia> {
        if (usuarioNick.isEmpty()) {
            throw Exception("El nick del usuario es obligatorio")
        }

        return noticiaService.listarNoticiasPorUsuario(usuarioNick)
    }

    fun buscarNoticiaPorTitulo(titulo: String): List<Noticia> {
        if (titulo.isBlank()) throw Exception("El titulo es obligatorio")

        return noticiaService.buscarNoticiaPorTitulo(titulo)
    }

    fun buscarNoticiasPorEtiquetas(etiquetas: List<String>): List<Noticia> {
        if (etiquetas.isEmpty()) {
            return emptyList()
        }
        return noticiaService.buscarNoticiasPorEtiquetas(etiquetas)
    }

    fun listarTodas(): List<Noticia> {
        return noticiaService.listarTodas()
    }

    fun listarUltimasNoticias(): List<Noticia> {
        return noticiaService.listarUltimasNoticias()
    }
}