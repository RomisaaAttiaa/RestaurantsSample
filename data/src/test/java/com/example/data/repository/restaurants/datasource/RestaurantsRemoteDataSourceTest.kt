package com.example.data.repository.restaurants.datasource

import com.example.data.data.entities.RestaurantAPIModel
import com.example.data.data.remote.ServiceGenerator
import com.example.data.data.remote.service.RestaurantApi
import com.example.domain.model.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RestaurantsRemoteDataSourceTest {

    private val serviceGenerator = mockk<ServiceGenerator>()

    private val remoteDataSource by lazy {
        RestaurantsRemoteDataSource(serviceGenerator)
    }

    @Test
    fun test_get_restaurants_success() = runBlocking {
        coEvery {
            serviceGenerator.createService(RestaurantApi::class.java).getRestaurants()
        } coAnswers {
            RestaurantAPIModel()
        }
        assert((remoteDataSource.getRestaurantsList() as? Result.Success)?.data == null)
    }

    @Test
    fun test_get_restaurants_failure() = runBlocking {
        val errorMsg = "Bad Request"
        coEvery {
            serviceGenerator.createService(RestaurantApi::class.java).getRestaurants()
        } coAnswers {
            throw Exception(errorMsg)
        }
        val result = remoteDataSource.getRestaurantsList()
        assert((result as? Result.Error)?.error?.message == errorMsg)
    }

}