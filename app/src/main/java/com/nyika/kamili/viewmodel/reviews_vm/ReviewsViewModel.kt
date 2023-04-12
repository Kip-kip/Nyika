package com.nyika.kamili.viewmodel.reviews_vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyika.kamili.database.reviews.ReviewsRepository
import com.nyika.kamili.network.KamiliApi
import com.nyika.kamili.utils.KamiliUiEvents
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    private val reviewsRepositoryImpl: ReviewsRepository,
    private val retrofitInstance: KamiliApi,
) : ViewModel() {

    private val _reviews = reviewsRepositoryImpl.getReviews()
    val reviews = _reviews

    private val _uiEvents =  Channel<KamiliUiEvents>()
    val uiEvents = _uiEvents

    val reviewsRef = Firebase.firestore.collection("Reviews")

    init {
        getFirebaseReviews()
    }

    private fun getReviews() {

        viewModelScope.launch {
            try {
                val remoteReviews = retrofitInstance.getKamiliReviews()
                remoteReviews.let { it
                    for (item in it.message) {
                       reviewsRepositoryImpl.insertReview(item)
                    }
                }
            } catch (e: IOException) {
                println("IO issue")
            } catch (e: HttpException) {
                println("HTTP issue")
            }

        }


    }

    private fun getFirebaseReviews(){

        reviewsRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                sendUiEvent(KamiliUiEvents.ShowSnackbar(message = firebaseFirestoreException!!.message.toString()))
            }
            querySnapshot?.let {
                for (document in it) {
                    viewModelScope.launch {
                        reviewsRepositoryImpl.insertReview(document.toObject())
                    }
                }
            }
        }

    }

    fun sendUiEvent(event: KamiliUiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }
}