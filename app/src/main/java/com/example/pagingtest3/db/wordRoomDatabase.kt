package com.example.pagingtest3.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Entityの指定
@Database(entities = [Word::class], version = 1)
abstract class wordRoomDatabase: RoomDatabase() {
    //DAOの指定
    abstract fun wordDao() : wordDao
    //DBのビルド
    companion object {
        fun buildDatabase(context: Context): wordRoomDatabase {
            return Room.databaseBuilder(
                context,
                wordRoomDatabase::class.java, "word_db"
            ).build()
        }
    }
}