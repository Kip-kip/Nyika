package com.example.kamili.database.kamili_services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: ServicesData)

    @Query("SELECT * FROM servicesdata")
    fun getServices(): Flow<List<ServicesData>>

    @Query("DELETE FROM servicesdata")
    suspend fun deleteServices()

    @Query("SELECT * FROM servicesdata where id=:id")
    suspend fun getServiceById(id: Int): ServicesData?
}