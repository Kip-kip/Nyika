package com.nyika.kamili.viewmodel.reviews_vm



import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyika.kamili.data.ReviewsData
import com.nyika.kamili.database.reviews.ReviewsRepository
import com.nyika.kamili.network.KamiliApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
