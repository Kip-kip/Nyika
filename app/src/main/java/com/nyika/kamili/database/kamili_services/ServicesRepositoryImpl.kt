package com.nyika.kamili.database.kamili_services

import com.nyika.kamili.data.ServicesData
import kotlinx.coroutines.flow.Flow

class ServicesRepositoryImpl(
    private val servicesDao: ServicesDao
): ServicesRepository {
    override suspend fun insertService(service: ServicesData) {
        servicesDao.insertService(service)
    }

    override fun getServices(): Flow<List<ServicesData>> {
        return servicesDao.getServices()
    }

    override suspend fun deleteServices() {
        servicesDao.deleteServices()
    }

    override suspend fun getServiceById(id: Int): ServicesData? {
       return servicesDao.getServiceById(id)
    }
}


