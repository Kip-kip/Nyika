package com.nyika.kamili.database.reviews

import com.nyika.kamili.data.ReviewsData
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