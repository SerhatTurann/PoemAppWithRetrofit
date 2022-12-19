package com.stturan.poemapplication.service.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stturan.poemapplication.model.Poet

@Dao
interface PoetDAO {

    @Insert
    suspend fun insertAll(vararg poet: Poet) : List<Long>

    @Query("SELECT * FROM poet")
    suspend fun getAllPoet() : List<Poet>

    @Query(value = "SELECT * FROM poet WHERE poet_id = :poet_id ")
    suspend fun getPoet(poet_id : String) : Poet

    @Query(value = "DELETE FROM poet")
    suspend fun deleteAllPoet()
}