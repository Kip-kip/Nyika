package com.example.kamili.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ReviewsResponse(
    val message: List<ReviewsData>
)
@Entity
data class ReviewsData(
    val score :Int?=null,
    @PrimaryKey val id : Int?=null,
    val comment : String?=null,
    val username : String?=null,
    val date : String?=null
){

}
