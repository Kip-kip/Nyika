package com.example.kamili.database.house_size

import com.example.kamili.data.HouseSizeData
import com.example.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

class HousesRepositoryImpl(
    private val housesDao: HousesDao
): HousesRepository {
    override suspend fun insertHouseSize(house: HouseSizeData) {
        housesDao.insertHouseSize(house)
    }

    override fun getHouseSizes(): Flow<List<HouseSizeData>> {
        return housesDao.getHouseSizes()
    }

    override suspend fun deleteHouseSizes() {
        housesDao.deleteHouseSizes()
    }

    override suspend fun getHouseSizeById(id: Int): HouseSizeData? {
        return housesDao.getHouseSizeById(id)
    }


}