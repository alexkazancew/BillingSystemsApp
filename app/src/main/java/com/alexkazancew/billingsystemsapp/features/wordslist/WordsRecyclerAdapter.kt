package com.alexkazancew.billingsystemsapp.features.wordslist

import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.alexkazancew.billingsystemsapp.R
import com.alexkazancew.billingsystemsapp.data.db.Word
import com.alexkazancew.billingsystemsapp.ui.CustomWordListItem


interface OnItemClickListener {

    fun onItemClick(word: Word)

    fun onItemLongClick(word: Word)
}

class WordsRecyclerAdapter(var words: ArrayList<Word>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: OnItemClickListener? = null

    fun deleteWord(wordId: String) {
        for (i in 0 until words.size) {
            if (words[i].uuid == wordId) {
                words.removeAt(i)
                notifyDataSetChanged()
                return
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordsViewHolder(view)
    }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WordsViewHolder) {
            holder.customWordView.bindWord(words[position])
            holder.itemView.setOnClickListener {
                listener?.onItemClick(words[position])
            }

            holder.itemView.setOnLongClickListener {
                listener?.onItemLongClick(words[position])
                true
            }
        }
    }

    inner class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var customWordView = itemView as CustomWordListItem
    }

}