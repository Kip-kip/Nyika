package com.nyika.kamili.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ArticlesResponse(
    val message: List<ArticlesData>
)
@Entity
data class ArticlesData(
    val title : String?=null,
    val content : String?=null,
    val writer : String?=null,
    val image : String?=null,
    val date : String?=null,
    val category: String?=null,
    @PrimaryKey val id : Int ?=null
){

}
