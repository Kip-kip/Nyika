package com.example.kamili.database.articles

import com.example.kamili.data.ArticlesData
import com.example.kamili.data.ReviewsData
import kotlinx.coroutines.flow.Flow

class ArticlesRepositoryImpl(
    private val articlesDao: ArticlesDao
): ArticlesRepository {

    override suspend fun insertArticle(article: ArticlesData) {
        articlesDao.insertArticle(article)
    }

    override fun getArticles(): Flow<List<ArticlesData>> {
        return articlesDao.getArticles()
    }

    override suspend fun deleteArticles() {
        articlesDao.deleteArticles()
    }

    override suspend fun getArticleById(id: Int): ArticlesData? {
        return articlesDao.getArticleById(id)
    }


}