package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SortingValues(
    val bestMatch: Double,
    val newest: Double,
    val ratingAverage: Double,
    val distance: Double,
    val popularity: Double,
    val averageProductPrice: Double,
    val deliveryCosts: Double,
    val minCost: Double,
) : Parcelable

@Parcelize
data class Restaurant(
    val name: String,
    val status: String,
    var isFavorite: Boolean,
    val sortingValues: SortingValues
) : Parcelable
