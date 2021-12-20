package com.example.domain.usecases

import com.example.domain.model.Restaurant
import com.example.domain.model.Result
import com.example.domain.model.SortOptions
import com.example.domain.repository.RestaurantsRepository
import sortRestaurants
import javax.inject.Inject

class RestaurantsUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {

    suspend operator fun invoke(sortOption: SortOptions = SortOptions.BEST_MATCH): Result<List<Restaurant>> {

        val favoriteRestaurants =
            when (val result = restaurantsRepository.getFavoriteRestaurants()) {
                is Result.Error -> emptyList()
                is Result.Success -> result.data ?: emptyList()
            }

        return when (val result = restaurantsRepository.getRestaurantsList()) {
            is Result.Success -> {
                result.data?.let {
                    return Result.Success(
                        sortRestaurants(
                            sortOption,
                            updateFavoriteInRestaurantModel(favoriteRestaurants, it)
                        )
                    )
                } ?: result
            }

            is Result.Error -> {
                result
            }
        }
    }

    private fun updateFavoriteInRestaurantModel(
        favoriteRestaurants: List<String>,
        allRestaurants: List<Restaurant>
    ): List<Restaurant> {
        allRestaurants.forEach {
            val isFavorite = favoriteRestaurants.find { name ->
                name == it.name
            }
            if (isFavorite.isNullOrEmpty().not()) {
                it.isFavorite = true
            }
        }
        return allRestaurants
    }

}