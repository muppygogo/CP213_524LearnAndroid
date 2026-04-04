package com.example.randomapp.data.repository

import com.example.randomapp.data.lacal.entity.AppDatabase
import com.example.randomapp.data.local.entity.NameEntity
import kotlinx.coroutines.flow.Flow

class NameRepository(private val database: AppDatabase) {

    private val nameDao = database.nameDao()

    suspend fun insertName(name: NameEntity): Long {
        return nameDao.insert(name)
    }

    suspend fun updateName(name: NameEntity) {
        nameDao.update(name)
    }

    suspend fun deleteName(name: NameEntity) {
        nameDao.delete(name)
    }

    fun getAllNames(): Flow<List<NameEntity>> {
        return nameDao.getAllNames()
    }

    suspend fun getNameById(id: Int): NameEntity? {
        return nameDao.getNameById(id)
    }

    fun getNamesByUser(userId: Int): Flow<List<NameEntity>> {
        return nameDao.getNamesByUser(userId)
    }
}
