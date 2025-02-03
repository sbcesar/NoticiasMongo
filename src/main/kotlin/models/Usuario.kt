package org.example.entity

import org.bson.codecs.pojo.annotations.BsonProperty

data class Usuario(
    val _id: String,    //email
    val nombre: String,
    val apodo: String,
    val estado: Estado = Estado.ACTIVO,
    val direccion: Direccion,
    @BsonProperty("telefonos")
    val telefono: List<String>
)