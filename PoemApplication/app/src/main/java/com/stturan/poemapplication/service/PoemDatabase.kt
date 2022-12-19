package com.stturan.poemapplication.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stturan.poemapplication.model.Poem
import com.stturan.poemapplication.model.Poet
import com.stturan.poemapplication.service.dao.PoemDAO
import com.stturan.poemapplication.service.dao.PoetDAO

@Database(entities = arrayOf(Poem::class, Poet::class), version = 1)
abstract class PoemDatabase : RoomDatabase(){
    abstract fun poemDao() : PoemDAO
    abstract fun poetDao() : PoetDAO

    companion object{

        @Volatile private var instance : PoemDatabase? ? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }


        private fun createDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,PoemDatabase::class.java,"poemdatabase").build()
    }


}