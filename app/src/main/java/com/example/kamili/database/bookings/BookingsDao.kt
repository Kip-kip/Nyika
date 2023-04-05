package com.example.kamili.database.bookings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kamili.data.ArticlesData
import com.example.kamili.data.BookingData
import com.example.kamili.data.ReviewsData
import com.example.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingData)

    @Query("SELECT * FROM bookingdata")
    fun getBookings(): Flow<List<BookingData>>

    @Query("DELETE FROM bookingdata")
    suspend fun deleteBookings()

    @Query("SELECT * FROM bookingdata where id=:id")
    suspend fun getBookingById(id: String): BookingData?
}