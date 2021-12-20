package com.example.justeattakeawayapplication.presentation.restaurants

import android.app.Application
import com.example.domain.model.Result
import com.example.domain.model.SortOptions
import com.example.domain.usecases.FavoriteRestaurantUseCase
import com.example.domain.usecases.RestaurantsUseCase
import com.example.domain.usecases.SortRestaurantsUseCase
import com.example.justeattakeawayapplication.R
import com.example.justeattakeawayapplication.di.DispatcherBackground
import com.example.justeattakeawayapplication.di.DispatcherForeground
import com.example.justeattakeawayapplication.presentation.base.BaseViewModel
import com.example.justeattakeawayapplication.presentation.base.ConsumableValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    val context: Application,
    @DispatcherForeground val dispatcherForeground: CoroutineContext,
    @DispatcherBackground val dispatcherBackground: CoroutineContext,
    private val restaurantsUseCase: RestaurantsUseCase,
    private val favoriteRestaurantUseCase: FavoriteRestaurantUseCase,
    private val sortRestaurantUseCase: SortRestaurantsUseCase
) :
    BaseViewModel<RestaurantsAction, RestaurantsState>(dispatcherForeground, dispatcherBackground) {

    override fun handleAction(action: RestaurantsAction) {
        when (action) {
            is RestaurantsAction.LoadRestaurants -> {
                handleLoadRestaurantsAction(action)
            }
            is RestaurantsAction.FavoriteOrUnFavoriteRestaurant -> {
                handleFavoriteOrUnFavoriteRestaurantAction(action)
            }
            is RestaurantsAction.OpenRestaurantDetails -> {
                handleOpenRestaurantDetailsAction(action)
            }
            is RestaurantsAction.SortRestaurantBasedOnSortOption -> {
                handleSortRestaurantBasedOnSortOptionAction(action)
            }
        }
    }

    private fun handleSortRestaurantBasedOnSortOptionAction(action: RestaurantsAction.SortRestaurantBasedOnSortOption) {
        updateState(RestaurantsState.Loading)
        val sortedRestaurants =
            sortRestaurantUseCase(mapSortOptions(action.sortOption), action.restaurants)
        updateState(RestaurantsState.RestaurantListSorted(sortedRestaurants))
    }

    private fun handleOpenRestaurantDetailsAction(action: RestaurantsAction.OpenRestaurantDetails) {
        updateState(RestaurantsState.RestaurantDetailsNavigation(ConsumableValue(action.restaurant)))
    }

    private fun handleFavoriteOrUnFavoriteRestaurantAction(action: RestaurantsAction.FavoriteOrUnFavoriteRestaurant) {
        executeOnBackground {
            val restaurant = action.restaurant
            restaurant.isFavorite = restaurant.isFavorite.not()
            when (favoriteRestaurantUseCase(restaurant.name, restaurant.isFavorite)) {
                is Result.Error -> {
                    updateState(RestaurantsState.Error(context.getString(R.string.failure_in_favorite_restaurants)))
                }
                is Result.Success -> {
                    updateState(RestaurantsState.RestaurantFavoriteUpdate(restaurant))
                }
            }
        }
    }

    private fun handleLoadRestaurantsAction(action: RestaurantsAction.LoadRestaurants) {
        updateState(RestaurantsState.Loading)
        executeOnBackground {
            when (val result = restaurantsUseCase(mapSortOptions(action.sortOption))) {
                is Result.Error -> {
                    updateState(RestaurantsState.Error(context.getString(R.string.failure_in_loading_restaurants)))
                }

                is Result.Success -> {
                    updateState(
                        RestaurantsState.RestaurantsLoaded(
                            result.data ?: emptyList()
                        )
                    )
                }
            }
        }
    }

    private fun mapSortOptions(sortOption: Int?): SortOptions {
        return when (sortOption) {
            R.id.best_match_chip -> SortOptions.BEST_MATCH
            R.id.newest_chip -> SortOptions.NEWEST
            R.id.rating_average_chip -> SortOptions.RATING_AVERAGE
            R.id.distance_chip -> SortOptions.DISTANCE
            R.id.popularity_chip -> SortOptions.POPULARITY
            R.id.average_price_chip -> SortOptions.AVERAGE_PRODUCT_PRICE
            R.id.delivery_costs_chip -> SortOptions.DELIVERY_COSTS
            R.id.minimum_cost_chip -> SortOptions.MINIMUM_COST
            else -> SortOptions.BEST_MATCH
        }
    }

}
