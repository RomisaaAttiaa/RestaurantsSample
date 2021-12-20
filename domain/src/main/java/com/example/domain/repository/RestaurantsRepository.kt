package com.example.domain.repository

import com.example.domain.model.Restaurant
import com.example.domain.model.Result

interface RestaurantsRepository {
    suspend fun getRestaurantsList(): Result<List<Restaurant>>
    suspend fun favoriteRestaurant(restaurantName: String?, isFavourite: Boolean): Result<Unit>
    suspend fun getFavoriteRestaurants(): Result<List<String>>
}