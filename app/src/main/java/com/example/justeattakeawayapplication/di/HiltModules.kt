package com.example.justeattakeawayapplication.di

import android.app.Application
import androidx.room.Room
import com.example.data.data.local.RestaurantDatabase
import com.example.data.repository.restaurants.RestaurantsRepositoryImpl
import com.example.domain.repository.RestaurantsRepository
import com.example.justeattakeawayapplication.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

const val DATABASE_NAME = "Restaurant-Database"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherForeground


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherBackground

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRestaurantsRepositoryImpl(
        restaurantsRepositoryImpl: RestaurantsRepositoryImpl
    ): RestaurantsRepository

}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Application): RestaurantDatabase {
        return Room.databaseBuilder(
            context,
            RestaurantDatabase::class.java, DATABASE_NAME
        ).build()
    }

    @Provides
    @DispatcherForeground
    fun provideForegroundContext(): CoroutineContext = Dispatchers.Main

    @Provides
    @DispatcherBackground
    fun provideBackgroundContext(): CoroutineContext = Dispatchers.IO

}