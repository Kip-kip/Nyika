package com.nyika.kamili.viewmodel.articles_vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyika.kamili.database.articles.ArticlesRepository
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
class ArticlesViewModel @Inject constructor(
    private val articlesRepositoryImpl: ArticlesRepository,
    private val retrofitInstance: KamiliApi,
) : ViewModel() {

    private val _articles = articlesRepositoryImpl.getArticles()
    val articles = _articles


    private val _uiEvents =  Channel<KamiliUiEvents>()
    val uiEvents = _uiEvents

    val reviewsRef = Firebase.firestore.collection("Articles")

    init {
        getFirebaseArticles()
    }

    private fun getArticles() {

        viewModelScope.launch {
            try {
                articlesRepositoryImpl.deleteArticles()
                val remoteArticles = retrofitInstance.getKamiliArticles()
                remoteArticles.let { it
                    for (item in it.message) {
                        articlesRepositoryImpl.insertArticle(item)
                    }
                }
            } catch (e: IOException) {
                println("IOo issue")
            } catch (e: HttpException) {
                println("HTTP issue")
            }

        }


    }


    private fun getFirebaseArticles(){
        reviewsRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                sendUiEvent(KamiliUiEvents.ShowSnackbar(message = firebaseFirestoreException!!.message.toString()))
            }
            querySnapshot?.let {
                for (document in it) {
                    viewModelScope.launch {
                        articlesRepositoryImpl.insertArticle(document.toObject())
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