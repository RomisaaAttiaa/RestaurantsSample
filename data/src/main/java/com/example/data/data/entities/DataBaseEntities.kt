package com.example.data.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.SortingValues

@Entity
data class RestaurantEntity(
    @PrimaryKey val name: String,
    val status: String,
    @Embedded val sortingValues: SortingValues
)

@Entity
data class FavoriteRestaurantEntity(
    @PrimaryKey val name: String
)