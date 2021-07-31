package com.feluts.saludable.model

import java.io.Serializable

data class Trago(val drinks: List<Drink>): Serializable {
    data class Drink(
        val idDrink: String,
        val strDrink: String,
        val strDrinkThumb: String
    ): Serializable
}



