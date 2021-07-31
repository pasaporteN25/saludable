package com.feluts.saludable

import android.util.Log
import androidx.lifecycle.ViewModel
import com.feluts.saludable.api.ApiTragosImp
import com.feluts.saludable.model.Trago
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call

class ComidaViewModel : ViewModel(){

    fun insertardb(comida: Comidas){
        val dbcloud = FirebaseFirestore.getInstance()
        try{
            dbcloud.collection("comidas").document().set(
                hashMapOf("comida" to comida.comida,
                    "principal" to comida.principal,
                    "secundaria" to comida.secundaria,
                    "bebida" to comida.bebida,
                    "postre" to comida.postre,
                    "tentacion" to comida.tentacion,
                    "hambre" to comida.hambre,
                    "timestamp" to comida.fechayhora)
            )
        }catch (e:Exception){
            Log.e("Error al insertar",e.message.toString())
        }
        //pasar try catch al activity
    }

    fun getTrago():Call<Trago>{
        val api: ApiTragosImp = ApiTragosImp()
        return api.getTrago()
    }


    }