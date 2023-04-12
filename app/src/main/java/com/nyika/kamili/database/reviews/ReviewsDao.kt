package com.nyika.kamili.database.reviews

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyika.kamili.data.ReviewsData
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: ReviewsData)

    @Query("SELECT * FROM reviewsdata")
    fun getReviews(): Flow<List<ReviewsData>>

    @Query("DELETE FROM reviewsdata")
    suspend fun deleteReviews()

    @Query("SELECT * FROM reviewsdata where id=:id")
    suspend fun getReviewById(id: Int): ReviewsData?
}