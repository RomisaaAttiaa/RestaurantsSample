import com.example.domain.model.Restaurant
import com.example.domain.model.SortOptions

fun sortRestaurants(sortOptions: SortOptions, restaurants: List<Restaurant>): List<Restaurant> {
    return sortBasedOnFavorite(
        sortBasedOnOpeningStatus(
            sortBasedOnSortingOptions(
                sortOptions,
                restaurants
            )
        )
    )
}

fun sortBasedOnSortingOptions(
    sortOptions: SortOptions,
    restaurants: List<Restaurant>
): List<Restaurant> {
    return when (sortOptions) {
        SortOptions.BEST_MATCH, SortOptions.NEWEST, SortOptions.POPULARITY, SortOptions.RATING_AVERAGE -> sortRestaurantsByDescendingOrder(
            restaurants,
            sortOptions
        )
        SortOptions.DISTANCE, SortOptions.AVERAGE_PRODUCT_PRICE, SortOptions.DELIVERY_COSTS, SortOptions.MINIMUM_COST -> sortRestaurantsByAscendingOrder(
            restaurants,
            sortOptions
        )
    }
}

fun sortRestaurantsByAscendingOrder(
    restaurants: List<Restaurant>,
    sortOptions: SortOptions
) = restaurants.sortedBy {
    val sortingValues = it.sortingValues
    when (sortOptions) {
        SortOptions.DISTANCE -> sortingValues.distance
        SortOptions.AVERAGE_PRODUCT_PRICE -> sortingValues.averageProductPrice
        SortOptions.DELIVERY_COSTS -> sortingValues.deliveryCosts
        SortOptions.MINIMUM_COST -> sortingValues.minCost
        else -> sortingValues.bestMatch
    }
}

fun sortRestaurantsByDescendingOrder(
    restaurants: List<Restaurant>,
    sortOptions: SortOptions
) = restaurants.sortedByDescending {
    val sortingValues = it.sortingValues
    when (sortOptions) {
        SortOptions.BEST_MATCH -> sortingValues.bestMatch
        SortOptions.NEWEST -> sortingValues.newest
        SortOptions.POPULARITY -> sortingValues.popularity
        SortOptions.RATING_AVERAGE -> sortingValues.ratingAverage
        else -> sortingValues.bestMatch
    }
}

fun sortBasedOnOpeningStatus(
    restaurants: List<Restaurant>
): List<Restaurant> {
    return restaurants.filter {
        it.status == Constants.OPEN_STATE
    } + restaurants.filter {
        it.status == Constants.ORDER_AHEAD_STATE
    } + restaurants.filter {
        it.status == Constants.CLOSED_STATE
    }
}

fun sortBasedOnFavorite(
    restaurants: List<Restaurant>
): List<Restaurant> {
    return restaurants.sortedByDescending {
        it.isFavorite
    }
}

object Constants {
    const val OPEN_STATE = "open"
    const val ORDER_AHEAD_STATE = "order ahead"
    const val CLOSED_STATE = "closed"
}


