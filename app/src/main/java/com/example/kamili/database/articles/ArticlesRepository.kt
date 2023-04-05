package com.example.kamili.database.articles

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kamili.data.ArticlesData
import com.example.kamili.data.BookingData
import com.example.kamili.data.ReviewsData
import com.example.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun insertArticle(article: ArticlesData)
    fun getArticles(): Flow<List<ArticlesData>>
    suspend fun deleteArticles()
    suspend fun getArticleById(id: Int): ArticlesData?
}