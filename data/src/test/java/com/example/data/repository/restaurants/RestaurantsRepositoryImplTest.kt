package com.example.data.repository.restaurants

import com.example.data.repository.restaurants.datasource.RestaurantsLocalDataSource
import com.example.data.repository.restaurants.datasource.RestaurantsRemoteDataSource
import com.example.domain.model.Restaurant
import com.example.domain.model.Result
import com.example.domain.model.SortingValues
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RestaurantsRepositoryImplTest {

    private val remoteDataSource = mockk<RestaurantsRemoteDataSource>()
    private val localDataSource = mockk<RestaurantsLocalDataSource>()

    private val restaurantsRepositoryImpl by lazy {
        RestaurantsRepositoryImpl(remoteDataSource, localDataSource)
    }

    private val restaurants = listOf(
        Restaurant(
            name = "KFC", status = "closed", isFavorite = false, sortingValues = SortingValues(
                bestMatch = 3.0,
                newest = 96.0,
                ratingAverage = 4.5,
                distance = 1190.0,
                popularity = 17.0,
                averageProductPrice = 1536.0,
                deliveryCosts = 200.0,
                minCost = 1000.0
            )
        ),
        Restaurant(
            name = "MAC", status = "open", isFavorite = false, sortingValues = SortingValues(
                bestMatch = 5.0,
                newest = 96.0,
                ratingAverage = 4.5,
                distance = 1190.0,
                popularity = 17.0,
                averageProductPrice = 1536.0,
                deliveryCosts = 200.0,
                minCost = 1000.0
            )
        )
    )

    @Test
    fun test_load_restaurants_from_remote_data_source_success() = runBlocking {
        coEvery { remoteDataSource.getRestaurantsList() } coAnswers {
            Result.Success(restaurants)
        }

        coEvery { localDataSource.insertRestaurantsList(any()) } coAnswers {
            Result.Success(Unit)
        }

        val restaurantsList = restaurantsRepositoryImpl.getRestaurantsList()
        assert((restaurantsList as? Result.Success)?.data == restaurants)
    }


    @Test
    fun test_load_restaurants_from_remote_data_source_failure() = runBlocking {
        coEvery { remoteDataSource.getRestaurantsList() } coAnswers {
            Result.Error(Exception())
        }

        coEvery { localDataSource.getRestaurantsList() } coAnswers {
            Result.Success(emptyList())
        }

        val restaurantsList = restaurantsRepositoryImpl.getRestaurantsList()
        assert((restaurantsList as? Result.Success)?.data?.isEmpty() == true)
    }

    @Test
    fun test_favorite_restaurant_success() = runBlocking {
        coEvery { localDataSource.favoriteRestaurant("KFC", true) } coAnswers {
            Result.Success(Unit)
        }

        val result = restaurantsRepositoryImpl.favoriteRestaurant("KFC", true)
        assert(result is Result.Success)
    }

    @Test
    fun test_favorite_restaurant_failure() = runBlocking {
        coEvery { localDataSource.favoriteRestaurant("KFC", true) } coAnswers {
            Result.Error(Exception())
        }

        val result = restaurantsRepositoryImpl.favoriteRestaurant("KFC", true)
        assert(result is Result.Error)
    }


    @Test
    fun test_load_favorite_restaurants_success() = runBlocking {
        val favoriteRestaurants = listOf("KFC", "MAC")
        coEvery { localDataSource.getFavoriteRestaurants() } coAnswers {
            Result.Success(listOf("KFC", "MAC"))
        }

        val result = restaurantsRepositoryImpl.getFavoriteRestaurants()
        assert((result as? Result.Success)?.data == favoriteRestaurants)
    }

    @Test
    fun test_load_favorite_restaurants_failure() = runBlocking {
        coEvery { localDataSource.getFavoriteRestaurants() } coAnswers {
            Result.Error(Exception())
        }

        val result = restaurantsRepositoryImpl.getFavoriteRestaurants()
        assert(result is Result.Error)
    }
}

