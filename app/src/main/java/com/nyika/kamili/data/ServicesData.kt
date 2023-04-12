package com.nyika.kamili.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ServicesResponse(
    val message: List<ServicesData>
)
@Entity
data class ServicesData(
    val title: String? = null,
    val type: String? = null,
    val icon: String? =null,
    @PrimaryKey val id: Int? = null
){
}
