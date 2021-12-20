package com.example.justeattakeawayapplication.presentation.restaurantdetails

import com.example.domain.model.Restaurant
import com.example.justeattakeawayapplication.presentation.base.Action

sealed class RestaurantDetailsAction : Action {
    data class FavoriteOrUnFavoriteRestaurant(val restaurant: Restaurant) :
        RestaurantDetailsAction()

}