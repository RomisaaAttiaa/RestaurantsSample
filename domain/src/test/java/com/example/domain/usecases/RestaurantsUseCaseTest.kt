package com.example.domain.usecases

import com.example.domain.model.Restaurant
import com.example.domain.model.Result
import com.example.domain.model.SortingValues
import com.example.domain.repository.RestaurantsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RestaurantsUseCaseTest {

    private val restaurantsRepository = mockk<RestaurantsRepository>()

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
        ),
        Restaurant(
            name = "ATTACK",
            status = "order ahead",
            isFavorite = false,
            sortingValues = SortingValues(
                bestMatch = 4.0,
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
            name = "HUT", status = "closed", isFavorite = false, sortingValues = SortingValues(
                bestMatch = 10.0,
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
            name = "ROSTO", status = "open", isFavorite = false, sortingValues = SortingValues(
                bestMatch = 2.0,
                newest = 96.0,
                ratingAverage = 4.5,
                distance = 1190.0,
                popularity = 17.0,
                averageProductPrice = 1536.0,
                deliveryCosts = 200.0,
                minCost = 1000.0
            )
        ),
    )

    private val restaurantUseCase by lazy {
        RestaurantsUseCase(restaurantsRepository)
    }

    @Test
    fun test_load_restaurants_success() = runBlocking {
        val favoriteRestaurant = Result.Success(listOf("MAC"))

        coEvery { restaurantsRepository.getFavoriteRestaurants() } coAnswers {
            favoriteRestaurant
        }
        coEvery { restaurantsRepository.getRestaurantsList() } coAnswers {
            Result.Success(restaurants)
        }

        val result = restaurantUseCase.invoke()
        assert(result is Result.Success)
        val data = (result as? Result.Success)?.data ?: emptyList()
        assert((data.size == 5 && data[0].name == "MAC" && data[1].name == "ROSTO" && data[2].name == "ATTACK" && data[3].name == "HUT" && data[4].name == "KFC"))
    }


    @Test
    fun test_load_restaurants_failure() = runBlocking {
        val errorMsg = "Bad Request"
        val favoriteRestaurant = Result.Success(listOf("MAC"))

        coEvery { restaurantsRepository.getFavoriteRestaurants() } coAnswers {
            favoriteRestaurant
        }
        coEvery { restaurantsRepository.getRestaurantsList() } coAnswers {
            Result.Error(Exception(errorMsg))
        }

        val result = restaurantUseCase.invoke()
        assert((result as? Result.Error)?.error?.message == errorMsg)
    }
}