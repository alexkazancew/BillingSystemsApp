package com.alexkazancew.billingsystemsapp.features.wordslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexkazancew.billingsystemsapp.R
import com.alexkazancew.billingsystemsapp.data.db.Word
import com.alexkazancew.billingsystemsapp.di.Injectable
import com.alexkazancew.billingsystemsapp.features.editword.EditWordFragment
import com.alexkazancew.billingsystemsapp.ui.DeleteConfirmListener
import com.alexkazancew.billingsystemsapp.ui.DeleteConfirmationDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class WordsListFragment : Fragment(), Injectable, OnItemClickListener, DeleteConfirmListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: WordsListViewModel

    private lateinit var recycler: RecyclerView
    private val adapter = WordsRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelFactory.create(WordsListViewModel::class.java)
        adapter.listener = this
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_words_list_layout, container, false)
        recycler = v.findViewById(R.id.words_list)
        var emptyView = v.findViewById<AppCompatTextView>(R.id.empty_view)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        viewModel.getWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isNotEmpty()) {
                        emptyView.visibility = View.INVISIBLE
                        recycler.visibility = View.VISIBLE
                        adapter.words = ArrayList(it)
                        adapter.notifyDataSetChanged()
                    } else {
                        recycler.visibility = View.INVISIBLE
                        emptyView.visibility = View.VISIBLE
                    }
                }


        activity?.setTitle(R.string.dictionary_title)
        setHasOptionsMenu(true)
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.add_word_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.add_word_menu_item) {
            openAddWordFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAddWordFragment() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.addWordFragment)
    }

    override fun onItemClick(word: Word) {
        val args = Bundle()
        args.putParcelable(EditWordFragment.WORD_ID_KEY, word)
        NavHostFragment.findNavController(this)
                .navigate(R.id.editWordFragment, args)
    }

    override fun onItemLongClick(word: Word) {
        val dialog = DeleteConfirmationDialog.getInstance(word)
        dialog.setTargetFragment(this, 0)
        dialog.show(fragmentManager, "")
    }

    @SuppressLint("CheckResult")
    override fun onConfirmDeleting(wordId: String) {
        viewModel.deleteWord(wordId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.deleteWord(wordId)
                }
    }

    override fun onCancelDeleting(wordId: String) {}
}