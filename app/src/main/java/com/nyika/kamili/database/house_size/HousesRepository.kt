package com.nyika.kamili.database.house_size

import com.nyika.kamili.data.HouseSizeData
import kotlinx.coroutines.flow.Flow

interface HousesRepository {
    suspend fun insertHouseSize(house: HouseSizeData)
    fun getHouseSizes(): Flow<List<HouseSizeData>>
    suspend fun deleteHouseSizes()
    suspend fun getHouseSizeById(id:Int): HouseSizeData?
}