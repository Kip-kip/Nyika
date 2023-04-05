package com.example.kamili.ui.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kamili.R
import com.example.kamili.data.ArticlesData
import com.example.kamili.data.BookingData
import com.example.kamili.data.ServicesData
import com.example.kamili.ui.articles.components.ArticleCard
import com.example.kamili.ui.booking.components.BookingCard
import com.example.kamili.ui.booking.components.BookingNavigator
import com.example.kamili.ui.theme.KamiliWhite
import com.example.kamili.utils.ui.SimpleAppBar
import com.example.kamili.viewmodel.book_vm.BookViewModel
import com.example.kamili.viewmodel.book_vm.BookViewModelEvents
import kotlin.math.truncate

@Composable
fun MyBookings(
    onPopBackStack: () -> Unit,
    onBookingClicked: (bookingId: String) -> Unit,
    viewModel: BookViewModel = hiltViewModel()
) {
    val fontName = FontFamily(Font(R.font.raleway_regular))
// icon = R.drawable.kamilipackage
//    icon = R.drawable.carpetcleaning

    val bookingStatus = viewModel.bookingStatus.value

    Column(
        modifier = Modifier
            .background(KamiliWhite)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        SimpleAppBar(R.drawable.article, Color.Black, 0.1f) {
            onPopBackStack()
        }
        BookingNavigator(fontName, bookingStatus, onPendingClick = {
            viewModel.receiveUIEvents(BookViewModelEvents.OnPendingClicked)
        }) {
            viewModel.receiveUIEvents(BookViewModelEvents.OnCompletedClicked)
        }

        Column(
            modifier = Modifier
                .background(Color(0xffFBFBFD))
                .padding(top = 0.dp, bottom = 20.dp)
                .fillMaxSize()

        ) {
            if (bookingStatus == "Completed")
                viewModel.bookings.collectAsState(initial = emptyList()).value.filter {
                    it.status == "Completed"
                }.forEach {
                    it
                    BookingCard(fontName = fontName, booking = it) {
                        onBookingClicked(it.id!!)
                    }
                }
            else
                viewModel.bookings.collectAsState(initial = emptyList()).value.filter {
                    it.status == "Pending"
                }.forEach {
                    it
                    BookingCard(fontName = fontName, booking = it) {
                        onBookingClicked(it.id!!)
                    }
                }
        }


    }
}