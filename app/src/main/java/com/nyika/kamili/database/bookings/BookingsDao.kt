package com.nyika.kamili.database.bookings

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyika.kamili.data.BookingData
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