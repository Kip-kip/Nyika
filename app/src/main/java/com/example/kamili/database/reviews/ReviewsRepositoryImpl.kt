package com.example.kamili.database.reviews

import com.example.kamili.data.ArticlesData
import com.example.kamili.data.ReviewsData
import com.example.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

class ReviewsRepositoryImpl(
    private val reviewsDao: ReviewsDao
): ReviewsRepository {
    override suspend fun insertReview(review: ReviewsData) {
        reviewsDao.insertReview(review)
    }

    override fun getReviews(): Flow<List<ReviewsData>> {
        return reviewsDao.getReviews()
    }

    override suspend fun deleteReviews() {
        reviewsDao.deleteReviews()
    }

    override suspend fun getReviewById(id: Int): ReviewsData? {
        return reviewsDao.getReviewById(id)
    }

}