package com.nyika.kamili.database.articles

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyika.kamili.data.ArticlesData
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticlesData)

    @Query("SELECT * FROM articlesdata")
    fun getArticles(): Flow<List<ArticlesData>>

    @Query("DELETE FROM articlesdata")
    suspend fun deleteArticles()

    @Query("SELECT * FROM articlesdata where id=:id")
    suspend fun getArticleById(id: Int): ArticlesData?
}