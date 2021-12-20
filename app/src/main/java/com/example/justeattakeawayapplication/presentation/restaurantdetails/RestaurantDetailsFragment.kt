package com.example.justeattakeawayapplication.presentation.restaurantdetails

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.justeattakeawayapplication.R
import com.example.justeattakeawayapplication.databinding.RestaurantDetailsFragmentBinding
import com.example.justeattakeawayapplication.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantDetailsFragment :
    BaseFragment<RestaurantDetailsFragmentBinding, RestaurantDetailsAction, RestaurantDetailsState, RestaurantDetailViewModel>() {

    override val viewModel: RestaurantDetailViewModel by viewModels()

    val restaurantDetails: RestaurantDetailsFragmentArgs by navArgs()

    override fun getViewBinding(): RestaurantDetailsFragmentBinding =
        RestaurantDetailsFragmentBinding.inflate(layoutInflater)


    override fun handleState(state: RestaurantDetailsState) {
        when (state) {
            is RestaurantDetailsState.Error -> {
                showError(state.errorMsg)
            }
            is RestaurantDetailsState.RestaurantFavoriteUpdate -> {
                binding?.restaurantFavoriteIv?.setImageResource(if (state.restaurant.isFavorite) R.drawable.ic_favorite else R.drawable.ic_un_favorite)
            }
        }
    }

    override fun initViews() {
        val restaurant = restaurantDetails.restaurant
        val values = restaurant.sortingValues
        binding?.apply {
            restaurantNameTv.text = restaurant.name
            restaurantOpeningStatusTv.text = restaurant.status
            restaurantDistanceTv.text = getString(R.string.distance, values.distance.toString())
            restaurantDeliveryCostTv.text = values.deliveryCosts.toString()
            restaurantRatingBar.rating = values.ratingAverage.toFloat()
            restaurantDetailsTv.text = getString(
                R.string.restaurant_details,
                restaurant.name,
                values.bestMatch.toString(),
                values.popularity.toString(),
                values.averageProductPrice.toString(),
                values.minCost.toString()
            )

            restaurantFavoriteIv.apply {
                setImageResource(if (restaurant.isFavorite) R.drawable.ic_favorite else R.drawable.ic_un_favorite)
                setOnClickListener {
                    viewModel.handleAction(
                        RestaurantDetailsAction.FavoriteOrUnFavoriteRestaurant(
                            restaurant
                        )
                    )
                }
            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        // Implementation Not Needed
    }

}