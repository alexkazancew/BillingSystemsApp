package com.alexkazancew.billingsystemsapp.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.alexkazancew.billingsystemsapp.R
import com.alexkazancew.billingsystemsapp.data.db.Word

class CustomWordListItem : ViewGroup {

    private lateinit var wordTextView: AppCompatTextView
    private lateinit var translationTextView: AppCompatTextView

    @Dimension(unit = Dimension.SP)
    private val textSize = 16f

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context?) {
        wordTextView = AppCompatTextView(context)
        wordTextView.textSize = textSize
        wordTextView.setTextColor(ContextCompat.getColor(context!!, R.color.colorBlack))
        addView(wordTextView)

        translationTextView = AppCompatTextView(this.context)
        translationTextView.textSize = textSize
        translationTextView.setTextColor(ContextCompat.getColor(context, R.color.colorBlack))
        addView(translationTextView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val newWidthMeasureSpec = getDefaultSize(this.suggestedMinimumWidth, widthMeasureSpec)
        val textViewWidth = (newWidthMeasureSpec / 2) - paddingLeft - paddingRight

        wordTextView.maxWidth = textViewWidth
        translationTextView.maxWidth = textViewWidth

        measureChild(wordTextView, textViewWidth, heightMeasureSpec)
        measureChild(translationTextView, textViewWidth, heightMeasureSpec)

        val heightUsed = paddingBottom + paddingTop + Math.max(wordTextView.measuredHeight, translationTextView.measuredHeight)

        setMeasuredDimension(newWidthMeasureSpec, heightUsed)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val widht = right - left
        val left = l + paddingLeft
        val right = r - paddingRight
        val center = widht / 2
        val verticalPadding = paddingTop + paddingBottom

        wordTextView.layout(
                left,
                paddingTop,
                left + wordTextView.measuredWidth,
                verticalPadding + wordTextView.measuredHeight)
        translationTextView.layout(
                center + paddingLeft,
                paddingTop,
                center + paddingLeft + translationTextView.measuredWidth,
                verticalPadding + translationTextView.measuredHeight)
    }

    fun bindWord(word: Word) {
        this.wordTextView.text = word.text
        this.translationTextView.text = word.translation
        invalidate()
    }
}