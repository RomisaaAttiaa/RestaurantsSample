package com.example.justeattakeawayapplication.presentation.restaurants

import com.example.domain.model.Restaurant
import com.example.justeattakeawayapplication.presentation.base.Action

sealed class RestaurantsAction : Action {
    data class LoadRestaurants(val sortOption: Int?) : RestaurantsAction()
    data class OpenRestaurantDetails(val restaurant: Restaurant) : RestaurantsAction()
    data class FavoriteOrUnFavoriteRestaurant(val restaurant: Restaurant) : RestaurantsAction()
    data class SortRestaurantBasedOnSortOption(
        val sortOption: Int,
        val restaurants: List<Restaurant>
    ) : RestaurantsAction()
}