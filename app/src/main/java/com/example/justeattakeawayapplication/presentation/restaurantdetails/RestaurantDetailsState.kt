package com.example.justeattakeawayapplication.presentation.restaurantdetails

import com.example.domain.model.Restaurant
import com.example.justeattakeawayapplication.presentation.base.State

sealed class RestaurantDetailsState : State {
    data class RestaurantFavoriteUpdate(val restaurant: Restaurant) : RestaurantDetailsState()
    data class Error(val errorMsg: String) : RestaurantDetailsState()
}