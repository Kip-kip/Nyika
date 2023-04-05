package com.example.kamili.database.kamili_services

import com.example.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

interface ServicesRepository {
    suspend fun insertService(service: ServicesData)
    fun getServices(): Flow<List<ServicesData>>
    suspend fun deleteServices()
    suspend fun getServiceById(id:Int): ServicesData?
}