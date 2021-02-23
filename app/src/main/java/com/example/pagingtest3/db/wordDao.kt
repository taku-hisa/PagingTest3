package com.example.pagingtest3.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface wordDao {

    //全件検索
    @Query("SELECT * FROM word_table")
    fun getItem(): PagingSource<Int, Word>

    //追加
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord (Word:Word)

}