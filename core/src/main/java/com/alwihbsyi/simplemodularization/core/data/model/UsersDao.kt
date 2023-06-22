package com.alwihbsyi.simplemodularization.core.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {
//  Untuk menyimpan data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(users: Users)

//  Untuk ambil data
    @Query("Select * From users order by id Desc")
    fun getAllUsername(): Flow<List<Users>>
}