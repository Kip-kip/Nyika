package com.example.kamili.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kamili.data.*
import com.example.kamili.database.articles.ArticlesDao
import com.example.kamili.database.bookings.BookingsDao
import com.example.kamili.database.house_size.HousesDao
import com.example.kamili.database.kamili_services.ServicesDao
import com.example.kamili.database.reviews.ReviewsDao

@Database(
    version = 24 ,
    entities = [ServicesData::class,HouseSizeData::class,ReviewsData::class,ArticlesData::class,BookingData::class]
)
//@TypeConverters(MyTypeConverter::class)
abstract class KamiliDatabase: RoomDatabase() {
    abstract val serviceDao : ServicesDao
    abstract val housesDao : HousesDao
    abstract val reviewsDao : ReviewsDao
    abstract val articlesDao : ArticlesDao
    abstract val bookingsDao : BookingsDao
}
