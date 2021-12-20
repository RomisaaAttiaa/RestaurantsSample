package com.example.justeattakeawayapplication.presentation.restaurants

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.Restaurant
import com.example.justeattakeawayapplication.databinding.RestaurantsFragmentBinding
import com.example.justeattakeawayapplication.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantsFragment :
    BaseFragment<RestaurantsFragmentBinding, RestaurantsAction, RestaurantsState, RestaurantsViewModel>() {

    override val viewModel: RestaurantsViewModel by viewModels()

    private val onRestaurantCardClicked: (Restaurant) -> Unit by lazy {
        {
            viewModel.handleAction(RestaurantsAction.OpenRestaurantDetails(it))
        }
    }

    private val onRestaurantFavouriteClicked: (Restaurant) -> Unit by lazy {
        {
            viewModel.handleAction(RestaurantsAction.FavoriteOrUnFavoriteRestaurant(it))
        }
    }

    private val restaurantsAdapter: RestaurantsAdapter by lazy {
        RestaurantsAdapter(mutableListOf(), onRestaurantCardClicked, onRestaurantFavouriteClicked)
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.shimmer?.shimmerLayout?.apply {
                visibility = View.VISIBLE
                startShimmer()
            }
            binding?.restaurantsRv?.visibility = View.GONE
        } else {
            binding?.shimmer?.shimmerLayout?.apply {
                stopShimmer()
                visibility = View.GONE
            }
            binding?.restaurantsRv?.visibility = View.VISIBLE
        }
    }

    override fun getViewBinding(): RestaurantsFragmentBinding {
        return RestaurantsFragmentBinding.inflate(layoutInflater)
    }

    override fun handleState(state: RestaurantsState) {
        when (state) {
            is RestaurantsState.Error -> {
                showLoading(false)
                showError(state.errorMsg)
            }
            RestaurantsState.Loading -> {
                showLoading(true)
            }
            is RestaurantsState.RestaurantsLoaded -> {
                showLoading(false)
                restaurantsAdapter.updateData(state.restaurants)
            }
            is RestaurantsState.RestaurantListSorted -> {
                showLoading(false)
                restaurantsAdapter.updateData(state.restaurants)
            }
            is RestaurantsState.RestaurantFavoriteUpdate -> {
                restaurantsAdapter.updateRestaurant(state.restaurant)
            }
            is RestaurantsState.RestaurantDetailsNavigation -> {
                navigateToRestaurantDetailsFragment(state)
            }
        }
    }

    private fun navigateToRestaurantDetailsFragment(state: RestaurantsState.RestaurantDetailsNavigation) {
        state.restaurant.consume {
            findNavController().navigate(
                RestaurantsFragmentDirections.actionRestaurantsFragmentToRestaurantDetailFragment(
                    it
                )
            )
        }
    }

    override fun initViews() {
        binding?.shimmer?.shimmerLayout?.startShimmer()
        binding?.chipsLayout?.childGroupChips?.setOnCheckedChangeListener { _, checkedId ->
            viewModel.handleAction(
                RestaurantsAction.SortRestaurantBasedOnSortOption(
                    checkedId,
                    restaurantsAdapter.restaurants
                )
            )
        }

        initRestaurantsRV()
    }

    override fun onStart() {
        super.onStart()
        viewModel.handleAction(RestaurantsAction.LoadRestaurants(binding?.chipsLayout?.childGroupChips?.checkedChipId))
    }

    private fun initRestaurantsRV() {
        binding?.restaurantsRv?.apply {
            layoutManager = LinearLayoutManager(this@RestaurantsFragment.requireContext())
            adapter = restaurantsAdapter
        }
    }

}