package com.alexkazancew.billingsystemsapp.features.wordslist

import androidx.lifecycle.ViewModel
import com.alexkazancew.billingsystemsapp.data.db.Word
import com.alexkazancew.billingsystemsapp.data.repositories.WordsRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class WordsListViewModel @Inject constructor(var wordsRepository: WordsRepository) : ViewModel() {

    fun getWords(): Flowable<List<Word>> {
        return wordsRepository.getWords()
    }

    fun deleteWord(wordId: String): Completable {
        return this.wordsRepository.deleteWordById(wordId)
    }
}