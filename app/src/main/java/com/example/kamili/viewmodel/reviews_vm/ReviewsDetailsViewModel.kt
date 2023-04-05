package com.example.kamili.viewmodel.reviews_vm



import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kamili.data.ArticlesData
import com.example.kamili.data.BookingData
import com.example.kamili.data.ReviewsData
import com.example.kamili.database.articles.ArticlesRepository
import com.example.kamili.database.articles.ArticlesRepositoryImpl
import com.example.kamili.database.bookings.BookingsRepository
import com.example.kamili.database.house_size.HousesRepository
import com.example.kamili.database.reviews.ReviewsRepository
import com.example.kamili.database.reviews.ReviewsRepositoryImpl
import com.example.kamili.network.KamiliApi
import com.example.kamili.utils.KamiliUiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject


@HiltViewModel
class ReviewsDetailsViewModel @Inject constructor(
    private val retrofitInstance: KamiliApi,
    private val reviewsRepositoryImpl: ReviewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //booking by id
    var reviewById = ReviewsData()

    init {
        val reviewId: Int = savedStateHandle.get<Int>("reviewId")!!
        getReviewPerId(reviewId)

    }


    private fun getReviewPerId(reviewId: Int){
        viewModelScope.launch {
            reviewById = reviewsRepositoryImpl.getReviewById(reviewId)!!
        }
    }



}
