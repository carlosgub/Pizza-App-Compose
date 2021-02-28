package com.carlosgub.pizzaappcompose.data

import com.carlosgub.pizzaappcompose.R
import com.carlosgub.pizzaappcompose.model.Pizza

class DataDummy{
    companion object{
        fun getTopMenuPizza():List<Pizza>{
            return listOf(
                Pizza(
                    image = R.drawable.pizza_1,
                    name = "Tommato Bacon Cake",
                    category = "Pizza",
                    price = 6.58f
                ),
                Pizza(
                    image = R.drawable.pizza_1,
                    name = "Tommato Bacon Cake",
                    category = "Pizza",
                    price = 6.58f
                ),
                Pizza(
                    image = R.drawable.pizza_1,
                    name = "Tommato Bacon Cake",
                    category = "Pizza",
                    price = 6.58f
                ),
                Pizza(
                    image = R.drawable.pizza_1,
                    name = "Tommato Bacon Cake",
                    category = "Pizza",
                    price = 6.58f
                )
            )
        }
    }
}