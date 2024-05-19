package com.example.vpet_tam.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vpet_tam.model.PetModel
import com.example.vpet_tam.model.RandomEventModel

@Database(entities = [PetModel::class, RandomEventModel::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun getDao() : DAOAccess
    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null
        fun getDataseClient(context: Context) : MyDatabase {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, MyDatabase::class.java, "MY_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()
                return INSTANCE!!
            }
        }
    }
}