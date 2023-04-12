package com.nyika.kamili.database.house_size

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyika.kamili.data.HouseSizeData
import kotlinx.coroutines.flow.Flow

@Dao
interface HousesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHouseSize(house: HouseSizeData)

    @Query("SELECT * FROM housesizedata")
    fun getHouseSizes(): Flow<List<HouseSizeData>>

    @Query("DELETE FROM housesizedata")
    suspend fun deleteHouseSizes()

    @Query("SELECT * FROM housesizedata where id=:id")
    suspend fun getHouseSizeById(id: Int): HouseSizeData?
}