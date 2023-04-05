package com.example.kamili.database.bookings

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kamili.data.ArticlesData
import com.example.kamili.data.BookingData
import com.example.kamili.data.ReviewsData
import com.example.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

interface BookingsRepository {
    suspend fun insertBooking(booking: BookingData)
    fun getBookings(): Flow<List<BookingData>>
    suspend fun deleteBookings()
    suspend fun getBookingById(id: String): BookingData?
}