package com.example.kamili.database.house_size

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kamili.data.HouseSizeData
import com.example.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

interface HousesRepository {
    suspend fun insertHouseSize(house: HouseSizeData)
    fun getHouseSizes(): Flow<List<HouseSizeData>>
    suspend fun deleteHouseSizes()
    suspend fun getHouseSizeById(id:Int): HouseSizeData?
}