package com.example.domain.usecases

import com.example.domain.model.SortOptions
import com.example.domain.model.Restaurant
import com.example.domain.model.SortingValues
import org.junit.Test

class SortRestaurantsUseCaseTest {

    private val sortRestaurantsUseCase by lazy {
        SortRestaurantsUseCase()
    }

    private val restaurants = listOf(
        Restaurant(
            name = "KFC", status = "closed", isFavorite = false, sortingValues = SortingValues(
                bestMatch = 3.0,
                newest = 100.0,
                ratingAverage = 10.0,
                distance = 1000.0,
                popularity = 17.0,
                averageProductPrice = 1500.0,
                deliveryCosts = 100.0,
                minCost = 1000.0
            )
        ),
        Restaurant(
            name = "MAC", status = "open", isFavorite = true, sortingValues = SortingValues(
                bestMatch = 5.0,
                newest = 96.0,
                ratingAverage = 3.5,
                distance = 1190.0,
                popularity = 17.0,
                averageProductPrice = 1536.0,
                deliveryCosts = 200.0,
                minCost = 1000.0
            )
        ),
        Restaurant(
            name = "ATTACK",
            status = "order ahead",
            isFavorite = false,
            sortingValues = SortingValues(
                bestMatch = 4.0,
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
            name = "HUT", status = "closed", isFavorite = false, sortingValues = SortingValues(
                bestMatch = 10.0,
                newest = 96.0,
                ratingAverage = 6.5,
                distance = 900.0,
                popularity = 10.0,
                averageProductPrice = 1536.0,
                deliveryCosts = 50.0,
                minCost = 900.0
            )
        ),
        Restaurant(
            name = "ROSTO", status = "open", isFavorite = false, sortingValues = SortingValues(
                bestMatch = 2.0,
                newest = 96.0,
                ratingAverage = 6.5,
                distance = 1190.0,
                popularity = 17.0,
                averageProductPrice = 1536.0,
                deliveryCosts = 200.0,
                minCost = 1000.0
            )
        ),
    )

    @Test
    fun test_sort_with_best_match_option() {
        val sortedList = sortRestaurantsUseCase.invoke(SortOptions.BEST_MATCH, restaurants)
        assert((sortedList.size == 5 && sortedList[0].name == "MAC" && sortedList[1].name == "ROSTO" && sortedList[2].name == "ATTACK" && sortedList[3].name == "HUT" && sortedList[4].name == "KFC"))
    }

    @Test
    fun test_sort_with_newest_option() {
        val sortedList = sortRestaurantsUseCase.invoke(SortOptions.NEWEST, restaurants)
        assert((sortedList.size == 5 && sortedList[0].name == "MAC" && sortedList[1].name == "ROSTO" && sortedList[2].name == "ATTACK" && sortedList[3].name == "KFC" && sortedList[4].name == "HUT"))
    }

    @Test
    fun test_sort_with_rating_average_option() {
        val sortedList = sortRestaurantsUseCase.invoke(SortOptions.RATING_AVERAGE, restaurants)
        assert((sortedList.size == 5 && sortedList[0].name == "MAC" && sortedList[1].name == "ROSTO" && sortedList[2].name == "ATTACK" && sortedList[3].name == "KFC" && sortedList[4].name == "HUT"))
    }

    @Test
    fun test_sort_with_distance_option() {
        val sortedList = sortRestaurantsUseCase.invoke(SortOptions.DISTANCE, restaurants)
        assert((sortedList.size == 5 && sortedList[0].name == "MAC" && sortedList[1].name == "ROSTO" && sortedList[2].name == "ATTACK" && sortedList[3].name == "HUT" && sortedList[4].name == "KFC"))
    }

    @Test
    fun test_sort_with_popularity_option() {
        val sortedList = sortRestaurantsUseCase.invoke(SortOptions.POPULARITY, restaurants)
        assert((sortedList.size == 5 && sortedList[0].name == "MAC" && sortedList[1].name == "ROSTO" && sortedList[2].name == "ATTACK" && sortedList[3].name == "KFC" && sortedList[4].name == "HUT"))
    }

    @Test
    fun test_sort_with_average_product_price_option() {
        val sortedList = sortRestaurantsUseCase.invoke(SortOptions.AVERAGE_PRODUCT_PRICE, restaurants)
        assert((sortedList.size == 5 && sortedList[0].name == "MAC" && sortedList[1].name == "ROSTO" && sortedList[2].name == "ATTACK" && sortedList[3].name == "KFC" && sortedList[4].name == "HUT"))
    }

    @Test
    fun test_sort_with_delivery_cost_option() {
        val sortedList = sortRestaurantsUseCase.invoke(SortOptions.DELIVERY_COSTS, restaurants)
        assert((sortedList.size == 5 && sortedList[0].name == "MAC" && sortedList[1].name == "ROSTO" && sortedList[2].name == "ATTACK" && sortedList[3].name == "HUT" && sortedList[4].name == "KFC"))
    }

    @Test
    fun test_sort_with_min_cost_option() {
        val sortedList = sortRestaurantsUseCase.invoke(SortOptions.MINIMUM_COST, restaurants)
        assert((sortedList.size == 5 && sortedList[0].name == "MAC" && sortedList[1].name == "ROSTO" && sortedList[2].name == "ATTACK" && sortedList[3].name == "HUT" && sortedList[4].name == "KFC"))
    }
}