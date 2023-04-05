package com.example.kamili.viewmodel.articles_vm


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kamili.data.ArticlesData
import com.example.kamili.data.BookingData
import com.example.kamili.database.articles.ArticlesRepository
import com.example.kamili.database.articles.ArticlesRepositoryImpl
import com.example.kamili.database.bookings.BookingsRepository
import com.example.kamili.database.house_size.HousesRepository
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
class ArticlesDetailsViewModel @Inject constructor(
    private val retrofitInstance: KamiliApi,
    private val articlesRepositoryImpl: ArticlesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //booking by id
    var articleById = ArticlesData()

    init {
        val articleId: Int = savedStateHandle.get<Int>("articleId")!!
        getBookingPerId(articleId)

    }


    private fun getBookingPerId(articleId: Int){
        viewModelScope.launch {
            articleById = articlesRepositoryImpl.getArticleById(articleId)!!
        }
    }



}
