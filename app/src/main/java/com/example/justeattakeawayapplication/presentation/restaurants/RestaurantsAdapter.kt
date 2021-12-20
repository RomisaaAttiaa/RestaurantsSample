package com.example.justeattakeawayapplication.presentation.restaurants

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Restaurant
import com.example.justeattakeawayapplication.R
import com.example.justeattakeawayapplication.databinding.RestaurantCardBinding

class RestaurantsAdapter(
    val restaurants: MutableList<Restaurant>,
    private val onRestaurantCardClicked: ((Restaurant) -> Unit)? = null,
    private val onRestaurantFavouriteClicked: ((Restaurant) -> Unit)? = null
) :
    RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        return RestaurantsViewHolder(RestaurantCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    fun updateData(restaurants: List<Restaurant>) {
        this.restaurants.clear()
        this.restaurants.addAll(restaurants)
        notifyDataSetChanged()
    }

    fun updateRestaurant(restaurant: Restaurant) {
        val index = restaurants.indexOfFirst {
            it.name == restaurant.name
        }
        restaurants[index] = restaurant
        notifyDataSetChanged()
    }

    inner class RestaurantsViewHolder(private val binding: RestaurantCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant) {
            binding.restaurantNameTv.text = restaurant.name
            binding.restaurantOpenStateTv.text = restaurant.status
            binding.restaurantDistanceTv.text =
                binding.root.context.getString(R.string.distance, restaurant.sortingValues.distance.toString())
            binding.restaurantRatingBar.rating = restaurant.sortingValues.ratingAverage.toFloat()
            binding.restaurantFavoriteIv.apply {
                setImageResource(if (restaurant.isFavorite) R.drawable.ic_favorite_white else R.drawable.ic_un_favorite_white)
                setOnClickListener {
                    onRestaurantFavouriteClicked?.invoke(restaurant)
                }
            }

            binding.root.setOnClickListener {
                onRestaurantCardClicked?.invoke(restaurant)
            }
        }
    }


}