package ru.animbus.ordomentis.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.animbus.ordomentis.data.database.dao.UnitDao
import ru.animbus.ordomentis.data.database.mappers.UnitMapper
import ru.animbus.ordomentis.data.models.domain.UnitData

class UnitRepository(
    private val unitDao: UnitDao,
    private val mapper: UnitMapper = UnitMapper()
) {
    suspend fun insertUnit(unit: UnitData) {
        unitDao.insertUnit(mapper.toEntity(unit))
    }

    suspend fun insertAllUnits(units: List<UnitData>) {
        unitDao.insertAllUnits(mapper.toEntityList(units))
    }

    suspend fun updateUnit(unit: UnitData) {
        unitDao.updateUnit(mapper.toEntity(unit))
    }

    suspend fun deleteUnit(unit: UnitData) {
        unitDao.deleteUnit(mapper.toEntity(unit))
    }

    suspend fun getUnitById(unitId: String): UnitData? {
        return unitDao.getUnitById(unitId)?.let { mapper.toDomain(it) }
    }

    suspend fun getUnitsByIds(unitIds: List<String>): List<UnitData> {
        return mapper.toDomainList(unitDao.getUnitsByIds(unitIds))
    }

    fun getAllUnits(): Flow<List<UnitData>> {
        return unitDao.getAllUnits().map { mapper.toDomainList(it) }
    }

    suspend fun getUnitsForOwner(ownerId: String): List<UnitData> {
        val allUnits = unitDao.getAllUnits().first()
        return mapper.toDomainList(allUnits).filter { unit ->
            unit.owners.contains(ownerId)
        }
    }

    suspend fun getUnitsForUser(userId: String): List<UnitData> {
        val allUnits = unitDao.getAllUnits().first()
        return mapper.toDomainList(allUnits).filter { unit ->
            unit.internal.contains(userId) || unit.external.contains(userId)
        }
    }

    suspend fun clearAllUnits() {
        unitDao.clearAllUnits()
    }

    suspend fun searchUnitsByName(name: String?): List<UnitData> {
        if (name.isNullOrBlank()) return emptyList()
        val allUnits = unitDao.getAllUnits().first()
        return mapper.toDomainList(allUnits).filter { unit ->
            // Здесь можно добавить логику поиска по имени
            // Пока что возвращаем все
            true
        }
    }

    suspend fun getUnitsByType(type: String): List<UnitData> {
        val allUnits = unitDao.getAllUnits().first()
        return mapper.toDomainList(allUnits).filter { unit ->
            unit.type.name.equals(type, ignoreCase = true)
        }
    }
}