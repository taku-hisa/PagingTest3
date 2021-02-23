package com.example.pagingtest3

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
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

        val Adapter = wordDataAdapter()

        lifecycleScope.launch {
            viewModel.getStream().collectLatest {
                Adapter.submitData(it)
            }
        }

        binding.recyclerView.apply {
            layoutManager =
                    when {
                        resources.configuration.orientation
                                == Configuration.ORIENTATION_PORTRAIT
                        -> GridLayoutManager(context, 2)
                        else
                        -> GridLayoutManager(context, 4)
                    }
            adapter = Adapter
        }
        binding.button.setOnClickListener {
            val wordText = binding.edText.text.toString()
            val word = Word(0, wordText)
            viewModel.insertWord(word)
        }
    }
}