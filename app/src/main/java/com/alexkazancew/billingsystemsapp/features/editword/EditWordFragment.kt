package com.alexkazancew.billingsystemsapp.features.editword

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexkazancew.billingsystemsapp.R
import com.alexkazancew.billingsystemsapp.data.db.Word
import com.alexkazancew.billingsystemsapp.BillingViewModelFactory
import com.alexkazancew.billingsystemsapp.di.Injectable
import com.alexkazancew.billingsystemsapp.features.HintsRecyclerAdapter
import com.alexkazancew.billingsystemsapp.features.OnHintClicklistener
import com.alexkazancew.billingsystemsapp.features.wordslist.WordsListFragment
import com.alexkazancew.billingsystemsapp.ui.DeleteConfirmListener
import com.alexkazancew.billingsystemsapp.ui.DeleteConfirmationDialog
import com.google.android.material.button.MaterialButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditWordFragment : Fragment(), Injectable, DeleteConfirmListener, OnHintClicklistener {

    companion object {
        val WORD_ID_KEY = "com.alexkazancew.WORD_ID"
    }

    @Inject
    lateinit var viewModelFactory: BillingViewModelFactory

    private lateinit var viewModel: EditWordViewModel

    private lateinit var wordText: AppCompatEditText
    private lateinit var translateText: AppCompatEditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var saveButton: MaterialButton
    private val hintAdapter = HintsRecyclerAdapter(listOf())
    private lateinit var word: Word


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = viewModelFactory.create(EditWordViewModel::class.java)
        hintAdapter.listener = this
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_edit_word_layout, container, false)
        wordText = v.findViewById(R.id.word_text)
        wordText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateHints(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        translateText = v.findViewById(R.id.translate_text)
        recyclerView = v.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = hintAdapter
        saveButton = v.findViewById(R.id.save_word_button)
        saveButton.setOnClickListener {
            viewModel.saveWord(Word(uuid = this.word.uuid, text = wordText.text.toString(), translation = translateText.text.toString()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        NavHostFragment.findNavController(this).navigate(R.id.wordsListFragment)
                    }
        }

        val word = arguments?.get(WORD_ID_KEY) as Word
        viewModel.getWord(word.uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    this.word = it
                    wordText.setText(it.text)
                    translateText.setText(it.translation)
                }


        activity?.setTitle(R.string.edit_word_title)
        setHasOptionsMenu(true)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.edit_word_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_word_menu_item) {
            openConfirmDeleteDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    fun openConfirmDeleteDialog() {
        val dialog = DeleteConfirmationDialog.getInstance(this.word)
        dialog.setTargetFragment(this, 0)
        dialog.show(fragmentManager, "")
    }

    @SuppressLint("CheckResult")
    override fun onConfirmDeleting(wordId: String) {
        viewModel.deleteWord(wordId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            NavHostFragment.findNavController(this)
                    .popBackStack()
        }
    }

    override fun onCancelDeleting(wordId: String) {}

    @SuppressLint("CheckResult")
    fun updateHints(word: String) {
        if (word.isNotBlank()) {
            viewModel.getHints(word)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ hints ->
                        hintAdapter.hints = hints
                        hintAdapter.notifyDataSetChanged()
                    }, { throwable ->
                        Log.e("EditWord", "Error: " + throwable.message)
                        throwable.printStackTrace()
                    })
        }
    }

    override fun onHintClick(hint: String) {
        translateText.setText(hint)
    }
}