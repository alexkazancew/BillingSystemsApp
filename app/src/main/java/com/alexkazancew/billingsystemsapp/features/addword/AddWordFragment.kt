package com.alexkazancew.billingsystemsapp.features.addword

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexkazancew.billingsystemsapp.R
import com.alexkazancew.billingsystemsapp.BillingViewModelFactory
import com.alexkazancew.billingsystemsapp.di.Injectable
import com.alexkazancew.billingsystemsapp.features.HintsRecyclerAdapter
import com.alexkazancew.billingsystemsapp.features.OnHintClicklistener
import com.alexkazancew.billingsystemsapp.features.wordslist.WordsListFragment
import com.google.android.material.button.MaterialButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddWordFragment : Fragment(), Injectable, OnHintClicklistener {

    @Inject
    lateinit var viewModelFactory: BillingViewModelFactory

    private lateinit var viewModel: AddWordViewModel

    private lateinit var saveButton: MaterialButton
    private lateinit var wordText: AppCompatEditText
    private lateinit var translation: AppCompatEditText
    private lateinit var hintsRecycler: RecyclerView
    private lateinit var hintAdapter: HintsRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelFactory.create(AddWordViewModel::class.java)
        hintAdapter = HintsRecyclerAdapter(listOf())
        hintAdapter.listener = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_word_layout, container, false)
        hintsRecycler = view.findViewById(R.id.list)
        hintsRecycler.adapter = hintAdapter
        hintsRecycler.layoutManager = LinearLayoutManager(context)
        wordText = view.findViewById(R.id.word_text)
        wordText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateHints(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        translation = view.findViewById(R.id.translate_text)
        saveButton = view.findViewById(R.id.save_word_button)
        saveButton.setOnClickListener {
            saveWord()
        }

        activity?.setTitle(R.string.add_word_title)
        return view
    }

    @SuppressLint("CheckResult")
    fun updateHints(word: String) {
        if (word.isNotBlank()) {
            viewModel.getWordHints(word)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ hints ->
                        hintAdapter.hints = hints
                        hintAdapter.notifyDataSetChanged()
                    },
                            { throwable ->
                                Log.e("AddWord", "Error: " + throwable.message)
                                throwable.printStackTrace()
                            }
                    )
        }

    }

    @SuppressLint("CheckResult")
    fun saveWord() {
        if (wordText.text.isNullOrEmpty()) {
            wordText.error = context?.getString(R.string.error_word_cant_be_emptu)
            return
        }

        if (translation.text.isNullOrEmpty()) {
            translation.error = context?.getString(R.string.error_translation_cant_be_emptu)
            return
        }

        viewModel.saveWord(wordText.text.toString(),
                translation.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    NavHostFragment.findNavController(this).popBackStack()
                }
    }

    override fun onHintClick(hint: String) {
        this.translation.setText(hint)
    }
}