package com.nyika.kamili.database.articles

import com.nyika.kamili.data.ArticlesData
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun insertArticle(article: ArticlesData)
    fun getArticles(): Flow<List<ArticlesData>>
    suspend fun deleteArticles()
    suspend fun getArticleById(id: Int): ArticlesData?
}