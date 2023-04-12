package com.nyika.kamili.database.reviews

import com.nyika.kamili.data.ReviewsData
import kotlinx.coroutines.flow.Flow

interface ReviewsRepository {
    suspend fun insertReview(review: ReviewsData)
    fun getReviews(): Flow<List<ReviewsData>>
    suspend fun deleteReviews()
    suspend fun getReviewById(id: Int): ReviewsData?
}