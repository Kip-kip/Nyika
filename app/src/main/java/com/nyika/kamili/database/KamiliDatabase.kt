package com.nyika.kamili.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nyika.kamili.data.*
import com.nyika.kamili.database.articles.ArticlesDao
import com.nyika.kamili.database.bookings.BookingsDao
import com.nyika.kamili.database.house_size.HousesDao
import com.nyika.kamili.database.kamili_services.ServicesDao
import com.nyika.kamili.database.reviews.ReviewsDao

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
