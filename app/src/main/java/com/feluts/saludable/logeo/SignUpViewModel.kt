package com.feluts.saludable.logeo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.feluts.saludable.model.User
import com.google.firebase.firestore.FirebaseFirestore

class SignUpViewModel : ViewModel() {



    fun insertardb(user: User){
        val dbcloud = FirebaseFirestore.getInstance()
        try{
            dbcloud.collection("usuarios").document().set(
                hashMapOf("nombre" to user.nombre,
                    "apellido" to user.apellido,
                    "dni" to user.dni,
                    "sexo" to user.sexo,
                    "bday" to user.bday,
                    "localidad" to user.localidad,
                    "usuario" to user.usuario,
                    "contraseña" to user.pass,
                    "tratamiento" to user.tratamiento)
            )
        }catch (e:Exception){
            Log.e("Error al insertar",e.message.toString())
        }
        //este try catch pasaria al fragment cuando implemente las dos vias sql y nosql
    }
}