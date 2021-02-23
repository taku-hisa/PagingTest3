package com.example.pagingtest3

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.pagingtest3.db.Word
import com.example.pagingtest3.db.wordDao
import com.example.pagingtest3.db.wordRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel (application: Application): AndroidViewModel(application) {

    private val dao: wordDao
    init {
        val db = wordRoomDatabase.buildDatabase(application) // DBにアクセスするclassで一度だけDBをビルドする
        dao = db.wordDao() // 使用するDaoを指定
    }

    /*
    LiveDataのような書き方　val getItem = dao.getItem()　ではだめ。
    Pagerにて、PagingSourceインスタンスを再利用するとエラーになる。
    An instance of PagingSource was re-used when Pager expected to create a new instance.
    Ensure that the pagingSourceFactory passed to Pager always returns a new instance of PagingSource.
     */
    private fun getItem():PagingSource<Int,Word>{
        return dao.getItem()
    }

    fun insertWord(Word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertWord(Word)
        }
    }

    fun getStream(): Flow<PagingData<Word>> {

        return Pager(
                config = PagingConfig(pageSize = 10),
                initialKey = 0
        ) {
            getItem()
        }.flow
    }
}