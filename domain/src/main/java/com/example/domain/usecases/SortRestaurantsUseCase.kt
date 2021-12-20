package com.example.domain.usecases

import com.example.domain.model.Restaurant
import com.example.domain.model.SortOptions
import sortRestaurants
import javax.inject.Inject


class SortRestaurantsUseCase @Inject constructor() {

    operator fun invoke(
        sortOption: SortOptions,
        restaurants: List<Restaurant>
    ): List<Restaurant> {
        return sortRestaurants(sortOption, restaurants)
    }

}