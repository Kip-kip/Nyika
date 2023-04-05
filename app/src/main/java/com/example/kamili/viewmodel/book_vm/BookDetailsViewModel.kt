package com.example.kamili.viewmodel.book_vm


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kamili.data.BookingData
import com.example.kamili.database.bookings.BookingsRepository
import com.example.kamili.database.house_size.HousesRepository
import com.example.kamili.network.KamiliApi
import com.example.kamili.utils.KamiliUiEvents
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject


@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val retrofitInstance: KamiliApi,
    private val housesRepositoryImpl: HousesRepository,
    private val bookingsRepositoryImpl: BookingsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //booking by id
    var bookingById = BookingData("")

    private val _selectedReviewScore = mutableStateOf(0)
    val selectedReviewScore = _selectedReviewScore

    private val _reviewComment = mutableStateOf("")
    val reviewComment = _reviewComment

    private val  _vmToUi= Channel<KamiliUiEvents>()
    val uiFromVm = _vmToUi.receiveAsFlow()
    private val bookingId: String = savedStateHandle.get<String>("bookingId")!!

    init {
        getBookingPerId(bookingId)

    }

    fun receiveUIEvents(event: BookViewModelEvents) {
        when (event) {
            is BookViewModelEvents.onReviewScoreSelected->{
                setSelectedReviewScore(event.score)
            }
            is BookViewModelEvents.onReviewSubmitted->{
                submitReview()
            }
            is BookViewModelEvents.onReviewComment->{
                updateReviewComment(event.comment)
            }
            else->{

            }
        }
    }

    private fun getBookingPerId(bookingId: String){
        viewModelScope.launch {
            bookingById = bookingsRepositoryImpl.getBookingById(bookingId)!!
        }
    }

    fun setSelectedReviewScore(score: Int){

        _selectedReviewScore.value = score
    }
    fun updateReviewComment(comment: String){
        _reviewComment.value = comment
    }

    fun submitReview(){
        val id = Firebase.firestore.collection("Bookings")
        id.document(bookingId).update("review_comment",reviewComment.value)
        id.document(bookingId).update("review_score",selectedReviewScore.value)
        id.document(bookingId).update("status","Completed")

        viewModelScope.launch {
            _vmToUi.send(KamiliUiEvents.ShowSnackbar("Your review has been submitted. Thank you!"))
        }
    }



    }
