package com.nyika.kamili.di

import android.app.Application
import androidx.room.Room
import com.nyika.kamili.database.house_size.HousesRepository
import com.nyika.kamili.database.house_size.HousesRepositoryImpl
import com.nyika.kamili.database.KamiliDatabase
import com.nyika.kamili.database.articles.ArticlesRepository
import com.nyika.kamili.database.articles.ArticlesRepositoryImpl
import com.nyika.kamili.database.bookings.BookingsRepository
import com.nyika.kamili.database.bookings.BookingsRepositoryImpl
import com.nyika.kamili.database.kamili_services.ServicesRepository
import com.nyika.kamili.database.kamili_services.ServicesRepositoryImpl
import com.nyika.kamili.database.reviews.ReviewsRepository
import com.nyika.kamili.database.reviews.ReviewsRepositoryImpl
import com.nyika.kamili.network.KamiliApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideServicesDatabase(application: Application): KamiliDatabase {
        return Room.databaseBuilder(
            application,
            KamiliDatabase::class.java,
            "kamili_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideServicesRepository(db: KamiliDatabase): ServicesRepository {
        return ServicesRepositoryImpl(db.serviceDao)
    }

    @Provides
    @Singleton
    fun provideHouseSizesRepository(db: KamiliDatabase): HousesRepository {
        return HousesRepositoryImpl(db.housesDao)
    }

    @Provides
    @Singleton
    fun provideReviewsRepository(db: KamiliDatabase): ReviewsRepository {
        return ReviewsRepositoryImpl(db.reviewsDao)
    }

    @Provides
    @Singleton
    fun provideArticlesRepository(db: KamiliDatabase): ArticlesRepository {
        return ArticlesRepositoryImpl(db.articlesDao)
    }

    @Provides
    @Singleton
    fun provideBookingsRepository(db: KamiliDatabase): BookingsRepository {
        return BookingsRepositoryImpl(db.bookingsDao)
    }

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        loggingInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor(loggingInterceptor)
            readTimeout(180,TimeUnit.SECONDS)
            build()
        }
    }



    @Provides
    fun provideRetrofitInstance(okHttpClient : OkHttpClient): KamiliApi {
        return Retrofit.Builder()
            .baseUrl("http://41.89.183.64/api/method/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(KamiliApi::class.java)
    }
}