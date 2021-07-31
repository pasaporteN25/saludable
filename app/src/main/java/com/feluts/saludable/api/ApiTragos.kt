package com.feluts.saludable.api

import com.feluts.saludable.model.Trago
import retrofit2.Call
import retrofit2.http.GET


interface ApiTragos {

    @GET("api/json/v1/1/random.php")
    fun getTrago(): Call<Trago>
}