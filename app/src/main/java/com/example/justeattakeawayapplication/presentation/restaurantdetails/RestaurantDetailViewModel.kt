package com.example.justeattakeawayapplication.presentation.restaurantdetails

import android.app.Application
import com.example.domain.model.Result
import com.example.domain.usecases.FavoriteRestaurantUseCase
import com.example.justeattakeawayapplication.R
import com.example.justeattakeawayapplication.di.DispatcherBackground
import com.example.justeattakeawayapplication.di.DispatcherForeground
import com.example.justeattakeawayapplication.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class RestaurantDetailViewModel @Inject constructor(
    val context: Application,
    @DispatcherForeground val dispatcherForeground: CoroutineContext,
    @DispatcherBackground val dispatcherBackground: CoroutineContext,
    private val favoriteRestaurantUseCase: FavoriteRestaurantUseCase,
) : BaseViewModel<RestaurantDetailsAction, RestaurantDetailsState>(
    dispatcherForeground,
    dispatcherBackground
) {

    override fun handleAction(action: RestaurantDetailsAction) {
        when (action) {
            is RestaurantDetailsAction.FavoriteOrUnFavoriteRestaurant -> {
                handleFavoriteOrUnFavoriteRestaurantAction(action)
            }
        }
    }


    private fun handleFavoriteOrUnFavoriteRestaurantAction(action: RestaurantDetailsAction.FavoriteOrUnFavoriteRestaurant) {
        executeOnBackground {
            val restaurant = action.restaurant
            restaurant.isFavorite = restaurant.isFavorite.not()
            when (favoriteRestaurantUseCase(restaurant.name, restaurant.isFavorite)) {
                is Result.Error -> {
                    updateState(RestaurantDetailsState.Error(context.getString(R.string.failure_in_favorite_restaurants)))
                }
                is Result.Success -> {
                    updateState(RestaurantDetailsState.RestaurantFavoriteUpdate(restaurant))
                }
            }
        }
    }
}