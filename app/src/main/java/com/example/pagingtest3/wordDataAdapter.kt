package com.example.pagingtest3

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingtest3.db.Word


class wordDataAdapter(private val context: Context) : PagingDataAdapter<Word, wordDataAdapter.wordViewHolder>(CALLBACK) {

    class wordViewHolder(wordView:View): RecyclerView.ViewHolder(wordView) {
        val wordView: TextView = itemView.findViewById(android.R.id.text1)
    }

    override fun onBindViewHolder(holder: wordViewHolder, position: Int) {
        holder.wordView.text = getItem(position)?.word
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): wordViewHolder {
        val wordView = View.inflate(parent.context, android.R.layout.simple_list_item_1, null)
        return wordViewHolder(wordView)
    }

}

val CALLBACK = object : DiffUtil.ItemCallback<Word>() {

    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}


