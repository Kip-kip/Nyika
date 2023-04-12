package com.nyika.kamili.database.bookings

import com.nyika.kamili.data.BookingData
import kotlinx.coroutines.flow.Flow

class BookingsRepositoryImpl(
    private val bookingsDao: BookingsDao
): BookingsRepository {
    override suspend fun insertBooking(booking: BookingData) {
        bookingsDao.insertBooking(booking)
    }

    override fun getBookings(): Flow<List<BookingData>> {
        return bookingsDao.getBookings()
    }

    override suspend fun deleteBookings() {
        bookingsDao.deleteBookings()
    }

    override suspend fun getBookingById(id: String): BookingData? {
        return bookingsDao.getBookingById(id)
    }


}