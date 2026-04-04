package com.example.randomapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.randomapp.data.local.entity.NameEntity

@Dao
interface NameDao {

    @Insert
    suspend fun insert(name: NameEntity)

    @Update
    suspend fun update(name: NameEntity)

    @Delete
    suspend fun delete(name: NameEntity)

    @Query("SELECT * FROM names")
    suspend fun getAllNames(): List<NameEntity>

    @Query("SELECT * FROM names WHERE id = :id")
    suspend fun getNameById(id: Int): NameEntity?
}
