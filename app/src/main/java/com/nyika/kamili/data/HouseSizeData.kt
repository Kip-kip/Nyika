package com.nyika.kamili.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class HouseSizeResponse(
    val message: List<HouseSizeData>
)
@Entity
data class HouseSizeData(
    val title: String?=null,
    @PrimaryKey val id: Int?=null,
    val price: Int?=null,

)
