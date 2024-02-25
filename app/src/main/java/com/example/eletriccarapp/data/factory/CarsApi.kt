package com.example.eletriccarapp.data.factory

import com.example.eletriccarapp.domain.entities.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {

    @GET("cars.json")
    fun getAllCars() : Call<List<Car>>
}