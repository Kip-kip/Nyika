package com.example.kamili.database.reviews

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kamili.data.ArticlesData
import com.example.kamili.data.ReviewsData
import com.example.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {
    suspend fun insertReview(review: ReviewsData)
    fun getReviews(): Flow<List<ReviewsData>>
    suspend fun deleteReviews()
    suspend fun getReviewById(id: Int): ReviewsData?
}