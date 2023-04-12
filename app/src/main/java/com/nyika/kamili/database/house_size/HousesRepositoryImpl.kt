package com.nyika.kamili.database.house_size

import com.nyika.kamili.data.HouseSizeData
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