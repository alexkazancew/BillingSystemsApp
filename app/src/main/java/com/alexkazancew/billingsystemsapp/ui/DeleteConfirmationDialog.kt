package com.alexkazancew.billingsystemsapp.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatTextView
import com.alexkazancew.billingsystemsapp.R
import com.alexkazancew.billingsystemsapp.data.db.Word
import com.google.android.material.button.MaterialButton

interface DeleteConfirmListener {
    fun onConfirmDeleting(wordId: String)
    fun onCancelDeleting(wordId: String)
}

class DeleteConfirmationDialog : AppCompatDialogFragment() {

    private var listener: DeleteConfirmListener? = null

    companion object {
        val WORD_KEY = "com.alexkazancew.WORD_KEY"

        fun getInstance(word: Word): AppCompatDialogFragment {
            val fragment = DeleteConfirmationDialog()
            val args = Bundle()
            args.putParcelable(WORD_KEY, word)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = activity?.layoutInflater?.inflate(R.layout.dialog_delete_word_confirmation_layout, container, false)
        var confirmationText = view?.findViewById<AppCompatTextView>(R.id.confirmation_delete_text)

        val word = arguments?.get(WORD_KEY) as Word
        confirmationText?.text = String.format(getString(R.string.delet_confirmation_text, word.text))

        val confirmDelete = view?.findViewById<MaterialButton>(R.id.confirm_delete_button)
        confirmDelete?.setOnClickListener {
            this.listener?.onConfirmDeleting(wordId = word.uuid)
            this.dismiss()
        }

        val cancelDeleteButton = view?.findViewById<MaterialButton>(R.id.cancel_delete_button)
        cancelDeleteButton?.setOnClickListener {
            this.listener?.onCancelDeleting(wordId = word.uuid)
            this.dismiss()
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            listener = targetFragment as DeleteConfirmListener
        } catch (ex: ClassCastException) {
            Log.e("DeleteWordDialog", ex.toString(), ex)
        }
    }
}