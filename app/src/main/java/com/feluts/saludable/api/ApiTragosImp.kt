package com.feluts.saludable.api

import com.feluts.saludable.model.Trago
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTragosImp {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.thecocktaildb.com/").build()
    }

    fun getTrago(): Call<Trago>{
        return getRetrofit().create(ApiTragos::class.java).getTrago()
    }

}