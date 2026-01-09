package ru.animbus.ordomentis.data.repositories

import ru.animbus.ordomentis.data.database.dao.SyncDao
import ru.animbus.ordomentis.data.database.entities.SyncEntity
import ru.animbus.ordomentis.data.database.mappers.SyncDataMapper
import ru.animbus.ordomentis.data.webapi.models.SyncData

class SyncRepository(
    private val syncDao: SyncDao,
    private val mapper: SyncDataMapper,
) {

    suspend fun insertSync(syncData: SyncData) {
        syncDao.insertSync(mapper.toEntity(syncData))
    }

    suspend fun getLocalSyncData(): SyncData {
        return syncDao.getSyncById(SyncEntity.SYNC_NAME).let { mapper.toDomain(it) }
    }

}