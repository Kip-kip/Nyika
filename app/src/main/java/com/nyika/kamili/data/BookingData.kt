package com.nyika.kamili.data

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

data class BookingResponse(
    val message: List<BookingData>
)

@Entity
data class BookingData(
    @PrimaryKey @NonNull val id: String,
    val client: String?=null,
    val house_size: String?=null,
    val location: String?=null,
    @field:TypeConverters(MyTypeConverter::class)
    val services: List<ServicesData>
    = listOf(ServicesData()),
    val date: String?=null,
    val time: String?=null,
    val additional_info: String?=null,
    val status: String?=null,
    val price: Int?=null,
    val review_score: Int?=null,
    val review_comment: String?=null,
)

class MyTypeConverter{

    @TypeConverter
    fun listToJson(value: List<ServicesData>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<ServicesData>::class.java).toList()
}

