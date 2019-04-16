package com.alexkazancew.billingsystemsapp.features

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.alexkazancew.billingsystemsapp.R

interface OnHintClicklistener {
    fun onHintClick(hint: String)
}

class HintsRecyclerAdapter(var hints: List<String>) : RecyclerView.Adapter<HintsRecyclerAdapter.HintViewHolder>() {

    var listener: OnHintClicklistener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hint_layout, parent, false)


        return HintViewHolder(view)
    }

    override fun getItemCount() = hints.size

    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        holder.hintTextView.text = hints[position]
        holder.itemView.setOnClickListener {
            listener?.onHintClick(hints[position])
        }
    }

    inner class HintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hintTextView = itemView.findViewById<AppCompatTextView>(R.id.hint_text)!!
    }
}