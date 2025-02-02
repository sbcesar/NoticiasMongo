package org.example.entity

import org.bson.types.ObjectId
import java.util.*

data class Noticia(
    val _id: ObjectId,
    val titulo: String,
    val cuerpo: String,
    val fech_publicacion: Date,
    val autor: Usuario,
    val tag: List<String>
)
