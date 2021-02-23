package com.example.pagingtest3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagingtest3.databinding.ActivityMainBinding
import com.example.pagingtest3.db.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = wordDataAdapter(this)
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            getStream().collectLatest {
                adapter.submitData(it)
            }
        }
        /*lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                progressBar.isVisible =
                    (it.refresh is LoadState.Loading) or (it.append is LoadState.Loading)
                retryButton.isVisible =
                    (it.refresh is LoadState.Error) or (it.append is LoadState.Error)
            }
        }

        retryButton.setOnClickListener {
            adapter.retry()
        }*/
        binding.button.setOnClickListener {
            val wordText = binding.edText.text.toString()
            val word = Word(0 , wordText )
            viewModel.insertWord(word)
        }
    }

    private fun getStream(): Flow<PagingData<Word>> {

        return Pager(
            config = PagingConfig(pageSize = 10)
        ) {
            viewModel.getItem
        }.flow
    }
}