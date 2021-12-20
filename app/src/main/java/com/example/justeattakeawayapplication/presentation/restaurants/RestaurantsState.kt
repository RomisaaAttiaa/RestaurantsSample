package com.example.justeattakeawayapplication.presentation.restaurants

import com.example.domain.model.Restaurant
import com.example.justeattakeawayapplication.presentation.base.ConsumableValue
import com.example.justeattakeawayapplication.presentation.base.State

sealed class RestaurantsState : State {
    data class RestaurantsLoaded(val restaurants: List<Restaurant>) : RestaurantsState()
    data class Error(val errorMsg: String) : RestaurantsState()
    object Loading : RestaurantsState()
    data class RestaurantListSorted(val restaurants: List<Restaurant>) : RestaurantsState()
    data class RestaurantFavoriteUpdate(val restaurant: Restaurant) : RestaurantsState()
    data class RestaurantDetailsNavigation(val restaurant: ConsumableValue<Restaurant>) :
        RestaurantsState()
}