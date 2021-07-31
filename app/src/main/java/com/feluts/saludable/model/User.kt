package com.feluts.saludable.model

import java.io.Serializable

data class User(val nombre:String,val apellido:String, val dni:Int, val sexo:String, val bday:String
, val localidad:String, val usuario:String, val pass:String, val tratamiento:String):Serializable