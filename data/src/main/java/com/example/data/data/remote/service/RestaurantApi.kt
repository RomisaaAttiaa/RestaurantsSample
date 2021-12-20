package com.example.data.data.remote.service

import com.example.data.data.entities.RestaurantAPIModel
import retrofit2.http.GET

interface RestaurantApi {

    @GET("restaurants")
    suspend fun getRestaurants(): RestaurantAPIModel

}