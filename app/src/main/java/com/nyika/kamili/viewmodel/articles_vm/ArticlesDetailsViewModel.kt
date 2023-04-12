package com.nyika.kamili.viewmodel.articles_vm


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyika.kamili.data.ArticlesData
import com.nyika.kamili.database.articles.ArticlesRepository
import com.nyika.kamili.network.KamiliApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
