package com.stturan.poemapplication.service.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stturan.poemapplication.model.Poem

@Dao
interface PoemDAO {

    @Insert
    suspend fun insertAll(vararg poem: Poem) : List<Long>

    @Query(value = "SELECT * FROM poem")
    suspend fun getAllPoem() : List<Poem>

    @Query(value = "SELECT * FROM poem WHERE poem_id = :poem_id ")
    suspend fun getPoem(poem_id : String) : Poem

    @Query(value = "SELECT * FROM poem WHERE poet_id = :poet_id ")
    suspend fun getPoetsPoem(poet_id : String) : List<Poem>

    @Query(value = "DELETE FROM poem")
    suspend fun deleteAllPoem()

}