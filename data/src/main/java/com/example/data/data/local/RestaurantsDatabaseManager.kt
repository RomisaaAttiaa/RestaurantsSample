package com.example.data.data.local

import androidx.room.*
import com.example.data.data.entities.FavoriteRestaurantEntity
import com.example.data.data.entities.RestaurantEntity


@Database(entities = [RestaurantEntity::class, FavoriteRestaurantEntity::class], version = 1)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun getRestaurantDao(): RestaurantDao
}

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM RestaurantEntity")
    suspend fun loadAllRestaurants(): List<RestaurantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurants(users: List<RestaurantEntity>?)


    @Query("SELECT * FROM FavoriteRestaurantEntity")
    suspend fun loadAllFavoriteRestaurants(): List<FavoriteRestaurantEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRestaurant(favoriteRestaurant: FavoriteRestaurantEntity)

    @Delete
    suspend fun deleteFavoriteRestaurant(favoriteRestaurant: FavoriteRestaurantEntity)

}

