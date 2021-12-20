package com.example.data.repository.restaurants.datasource

import com.example.data.data.local.RestaurantDatabase
import com.example.domain.model.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RestaurantsLocalDataSourceTest {

    private val database = mockk<RestaurantDatabase>()

    private val localDataSource by lazy {
        RestaurantsLocalDataSource(database)
    }

    @Test
    fun test_get_restaurants_success() = runBlocking {
        coEvery {
            database.getRestaurantDao().loadAllRestaurants()
        } coAnswers {
            listOf()
        }
        assert(localDataSource.getRestaurantsList() is Result.Success)
    }

    @Test
    fun test_get_restaurants_failure() = runBlocking {
        val errorMsg = "database not supported"
        coEvery {
            database.getRestaurantDao().loadAllRestaurants()
        } coAnswers {
            throw Exception(errorMsg)
        }
        val result = localDataSource.getRestaurantsList()
        assert((result as? Result.Error)?.error?.message == errorMsg)
    }

    @Test
    fun test_insert_restaurants_success() = runBlocking {
        coEvery {
            database.getRestaurantDao().insertAllRestaurants(any())
        } coAnswers {
        }
        assert(localDataSource.insertRestaurantsList(listOf()) is Result.Success)
    }

    @Test
    fun test_insert_restaurants_failure() = runBlocking {
        val errorMsg = "database not supported"
        coEvery {
            database.getRestaurantDao().insertAllRestaurants(any())
        } coAnswers {
            throw Exception(errorMsg)

        }
        val result = localDataSource.insertRestaurantsList(listOf())
        assert((result as? Result.Error)?.error?.message == errorMsg)
    }

    @Test
    fun test_favorite_restaurants_success() = runBlocking {
        coEvery {
            database.getRestaurantDao().insertFavoriteRestaurant(any())
        } coAnswers {
        }
        assert(localDataSource.favoriteRestaurant("KFC", true) is Result.Success)
    }

    @Test
    fun test_favorite_restaurants_failure() = runBlocking {
        val errorMsg = "database not supported"
        coEvery {
            database.getRestaurantDao().insertFavoriteRestaurant(any())
        } coAnswers {
            throw Exception(errorMsg)

        }
        val result = localDataSource.favoriteRestaurant("KFC", true)
        assert((result as? Result.Error)?.error?.message == errorMsg)
    }
}