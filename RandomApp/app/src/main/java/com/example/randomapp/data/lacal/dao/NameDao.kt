package com.example.randomapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.randomapp.data.local.entity.NameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NameDao {

    @Insert
    suspend fun insert(name: NameEntity): Long

    @Update
    suspend fun update(name: NameEntity)

    @Delete
    suspend fun delete(name: NameEntity)

    @Query("SELECT * FROM names")
    fun getAllNames(): Flow<List<NameEntity>>

    @Query("SELECT * FROM names WHERE id = :id")
    suspend fun getNameById(id: Int): NameEntity?

    @Query("SELECT * FROM names WHERE userId = :userId ORDER BY createdAt DESC")
    fun getNamesByUser(userId: Int): Flow<List<NameEntity>>
}
