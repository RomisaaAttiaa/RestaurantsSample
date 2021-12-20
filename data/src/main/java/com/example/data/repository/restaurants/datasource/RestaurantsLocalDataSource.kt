package com.example.data.repository.restaurants.datasource

import com.example.data.data.entities.FavoriteRestaurantEntity
import com.example.data.data.entities.RestaurantEntity
import com.example.data.data.local.RestaurantDatabase
import com.example.domain.model.Result
import javax.inject.Inject

class RestaurantsLocalDataSource @Inject constructor(private val restaurantDatabase: RestaurantDatabase) {

    suspend fun getRestaurantsList(): Result<List<RestaurantEntity>> {
        return try {
            Result.Success(restaurantDatabase.getRestaurantDao().loadAllRestaurants())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun insertRestaurantsList(restaurants: List<RestaurantEntity>?): Result<Unit> {
        return try {
            restaurantDatabase.getRestaurantDao().insertAllRestaurants(restaurants)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun favoriteRestaurant(restaurantName: String?, isFavourite: Boolean): Result<Unit> {
        return try {
            restaurantName?.let {
                if (isFavourite) {
                    restaurantDatabase.getRestaurantDao()
                        .insertFavoriteRestaurant(FavoriteRestaurantEntity(it))
                } else {
                    restaurantDatabase.getRestaurantDao()
                        .deleteFavoriteRestaurant(FavoriteRestaurantEntity(it))
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getFavoriteRestaurants(): Result<List<String>> {
        return try {
            Result.Success(restaurantDatabase.getRestaurantDao().loadAllFavoriteRestaurants().map {
                it.name
            })
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}