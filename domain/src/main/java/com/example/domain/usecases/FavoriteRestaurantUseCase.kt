package com.example.domain.usecases

import com.example.domain.model.Result
import com.example.domain.repository.RestaurantsRepository
import javax.inject.Inject

class FavoriteRestaurantUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {

    suspend operator fun invoke(restaurantName: String, isFavorite: Boolean): Result<Unit> {
        return restaurantsRepository.favoriteRestaurant(restaurantName, isFavorite)
    }

}