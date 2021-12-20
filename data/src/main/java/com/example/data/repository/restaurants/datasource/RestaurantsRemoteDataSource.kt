package com.example.data.repository.restaurants.datasource

import com.example.data.data.remote.ServiceGenerator
import com.example.data.data.remote.service.RestaurantApi
import com.example.domain.model.Restaurant
import com.example.domain.model.Result
import javax.inject.Inject

class RestaurantsRemoteDataSource @Inject constructor(private val serviceGenerator: ServiceGenerator) {

    suspend fun getRestaurantsList(): Result<List<Restaurant>> {
        return try {
            val restaurants = serviceGenerator.createService(RestaurantApi::class.java)
                .getRestaurants().restaurants
            Result.Success(restaurants)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}