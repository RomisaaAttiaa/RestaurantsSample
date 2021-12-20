package com.example.data.repository.restaurants

import com.example.data.data.entities.RestaurantEntity
import com.example.data.repository.restaurants.datasource.RestaurantsLocalDataSource
import com.example.data.repository.restaurants.datasource.RestaurantsRemoteDataSource
import com.example.domain.model.Restaurant
import com.example.domain.model.Result
import com.example.domain.repository.RestaurantsRepository
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RestaurantsRemoteDataSource,
    private val localDataSource: RestaurantsLocalDataSource
) : RestaurantsRepository {

    override suspend fun getRestaurantsList(): Result<List<Restaurant>> {

        return when (val result = remoteDataSource.getRestaurantsList()) {
            is Result.Error -> {
                when (val localRestaurantsListResult = localDataSource.getRestaurantsList()) {
                    is Result.Error -> {
                        Result.Error(localRestaurantsListResult.error)
                    }
                    is Result.Success -> {
                        Result.Success(localRestaurantsListResult.data?.map {
                            Restaurant(it.name, it.status, false, it.sortingValues)
                        })
                    }
                }
            }

            is Result.Success -> {
                localDataSource.insertRestaurantsList(result.data?.map {
                    RestaurantEntity(it.name, it.status, it.sortingValues)
                })
                result
            }
        }

    }

    override suspend fun favoriteRestaurant(
        restaurantName: String?,
        isFavourite: Boolean
    ): Result<Unit> {
        return localDataSource.favoriteRestaurant(restaurantName, isFavourite)
    }

    override suspend fun getFavoriteRestaurants(): Result<List<String>> {
        return localDataSource.getFavoriteRestaurants()
    }

}