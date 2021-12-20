package com.example.domain.usecases

import com.example.domain.model.Result
import com.example.domain.repository.RestaurantsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FavoriteRestaurantUseCaseTest {

    private val restaurantsRepository = mockk<RestaurantsRepository>()

    private val favoriteRestaurantUseCase by lazy {
        FavoriteRestaurantUseCase(restaurantsRepository)
    }

    @Test
    fun test_favorite_restaurant_success() = runBlocking {
        coEvery { restaurantsRepository.favoriteRestaurant("KFC", true) } coAnswers {
            Result.Success(Unit)
        }

        val result = favoriteRestaurantUseCase.invoke("KFC", true)
        assert(result is Result.Success)
    }

    @Test
    fun test_favorite_restaurant_failure() = runBlocking {
        coEvery { restaurantsRepository.favoriteRestaurant("KFC", true) } coAnswers {
            Result.Error(Exception())
        }

        val result = favoriteRestaurantUseCase.invoke("KFC", true)
        assert(result is Result.Error)
    }

}