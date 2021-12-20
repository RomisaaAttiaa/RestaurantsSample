package com.example.justeattakeawayapplication.presentation.restaurants

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.Restaurant
import com.example.domain.model.Result
import com.example.domain.model.SortingValues
import com.example.domain.usecases.FavoriteRestaurantUseCase
import com.example.domain.usecases.RestaurantsUseCase
import com.example.domain.usecases.SortRestaurantsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.CoroutineContext

class RestaurantsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val dispatcherForeground: CoroutineContext = Dispatchers.Unconfined
    private val dispatcherBackground: CoroutineContext = Dispatchers.Unconfined
    private val restaurantsUseCase = mockk<RestaurantsUseCase>()
    private val favoriteRestaurantUseCase = mockk<FavoriteRestaurantUseCase>()
    private val sortRestaurantUseCase = mockk<SortRestaurantsUseCase>()
    private val context = mockk<Application>()

    private val viewModel by lazy {
        RestaurantsViewModel(
            context,
            dispatcherForeground,
            dispatcherBackground,
            restaurantsUseCase,
            favoriteRestaurantUseCase,
            sortRestaurantUseCase
        )
    }

    private val restaurants = listOf(
        Restaurant(
            name = "KFC", status = "closed", isFavorite = false, sortingValues = SortingValues(
                bestMatch = 3.0,
                newest = 96.0,
                ratingAverage = 4.5,
                distance = 1190.0,
                popularity = 17.0,
                averageProductPrice = 1536.0,
                deliveryCosts = 200.0,
                minCost = 1000.0
            )
        ),
        Restaurant(
            name = "MAC", status = "open", isFavorite = false, sortingValues = SortingValues(
                bestMatch = 5.0,
                newest = 96.0,
                ratingAverage = 4.5,
                distance = 1190.0,
                popularity = 17.0,
                averageProductPrice = 1536.0,
                deliveryCosts = 200.0,
                minCost = 1000.0
            )
        )
    )


    @Test
    fun test_handle_action_load_restaurant_success() = runBlocking {
        coEvery { restaurantsUseCase.invoke() } coAnswers {
            delay(2000)
            Result.Success(restaurants)
        }
        viewModel.handleAction(RestaurantsAction.LoadRestaurants(3))
        assert(viewModel.liveData.value is RestaurantsState.Loading)
        delay(2500)
        assert((viewModel.liveData.value as? RestaurantsState.RestaurantsLoaded)?.restaurants?.size == 2)
    }

    @Test
    fun test_handle_action_load_restaurant_failure() = runBlocking {
        val errorMsg = "Error Loading"
        coEvery { restaurantsUseCase.invoke(any()) } coAnswers {
            delay(2000)
            Result.Error(Exception(errorMsg))
        }
        every { context.getString(any()) } returns errorMsg

        viewModel.handleAction(RestaurantsAction.LoadRestaurants(3))
        assert(viewModel.liveData.value is RestaurantsState.Loading)
        delay(2500)
        assert((viewModel.liveData.value as? RestaurantsState.Error)?.errorMsg == errorMsg)
    }

    @Test
    fun test_handle_action_favorite_restaurant_Success() {
        coEvery { favoriteRestaurantUseCase.invoke("KFC", true) } coAnswers {
            Result.Success(Unit)
        }
        viewModel.handleAction(
            RestaurantsAction.FavoriteOrUnFavoriteRestaurant(
                Restaurant(
                    name = "KFC",
                    status = "closed",
                    isFavorite = false,
                    sortingValues = SortingValues(
                        bestMatch = 3.0,
                        newest = 96.0,
                        ratingAverage = 4.5,
                        distance = 1190.0,
                        popularity = 17.0,
                        averageProductPrice = 1536.0,
                        deliveryCosts = 200.0,
                        minCost = 1000.0
                    )
                )
            )
        )

        assert((viewModel.liveData.value as? RestaurantsState.RestaurantFavoriteUpdate)?.restaurant?.isFavorite == true)
    }

    @Test
    fun test_handle_action_favorite_restaurant_failure() {
        val errorMsg = "Error Favorite Restaurant"
        coEvery { favoriteRestaurantUseCase.invoke("KFC", true) } coAnswers {
            Result.Error(Exception(errorMsg))
        }

        every { context.getString(any()) } returns errorMsg

        viewModel.handleAction(
            RestaurantsAction.FavoriteOrUnFavoriteRestaurant(
                Restaurant(
                    name = "KFC",
                    status = "closed",
                    isFavorite = false,
                    sortingValues = SortingValues(
                        bestMatch = 3.0,
                        newest = 96.0,
                        ratingAverage = 4.5,
                        distance = 1190.0,
                        popularity = 17.0,
                        averageProductPrice = 1536.0,
                        deliveryCosts = 200.0,
                        minCost = 1000.0
                    )
                )
            )
        )

        assert((viewModel.liveData.value as? RestaurantsState.Error)?.errorMsg == errorMsg)
    }

    @Test
    fun test_handle_action_open_restaurant_Details() {
        val restaurant = Restaurant(
            name = "KFC",
            status = "closed",
            isFavorite = false,
            sortingValues = SortingValues(
                bestMatch = 3.0,
                newest = 96.0,
                ratingAverage = 4.5,
                distance = 1190.0,
                popularity = 17.0,
                averageProductPrice = 1536.0,
                deliveryCosts = 200.0,
                minCost = 1000.0
            )
        )
        viewModel.handleAction(
            RestaurantsAction.OpenRestaurantDetails(restaurant)
        )
        assert((viewModel.liveData.value as? RestaurantsState.RestaurantDetailsNavigation)?.restaurant?.data == restaurant)
    }

    @Test
    fun test_handle_action_sort_restaurant_Success() {
        every { sortRestaurantUseCase.invoke(any(), restaurants) } returns restaurants
        viewModel.handleAction(RestaurantsAction.SortRestaurantBasedOnSortOption(1, restaurants))
        assert((viewModel.liveData.value as? RestaurantsState.RestaurantListSorted)?.restaurants == restaurants)
    }
}
