package com.nyika.kamili.database.bookings

import com.nyika.kamili.data.BookingData
import kotlinx.coroutines.flow.Flow

interface BookingsRepository {
    suspend fun insertBooking(booking: BookingData)
    fun getBookings(): Flow<List<BookingData>>
    suspend fun deleteBookings()
    suspend fun getBookingById(id: String): BookingData?
}