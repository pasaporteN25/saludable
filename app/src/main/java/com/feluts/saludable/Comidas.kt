package com.feluts.saludable

import java.io.Serializable
//import java.time.LocalDate
//import java.time.LocalDateTime
//import java.util.*

data class Comidas(val id:Int = 0, val comida: String, val principal: String, val secundaria: String, val bebida: String,
                   val postre: String?, val tentacion: String?, val hambre: String, val fechayhora: String

):Serializable