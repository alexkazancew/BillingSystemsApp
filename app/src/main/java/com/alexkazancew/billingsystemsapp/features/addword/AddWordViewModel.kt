package com.alexkazancew.billingsystemsapp.features.addword

import androidx.lifecycle.ViewModel
import com.alexkazancew.billingsystemsapp.data.db.Word
import com.alexkazancew.billingsystemsapp.data.repositories.HintsRepository
import com.alexkazancew.billingsystemsapp.data.repositories.WordsRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class AddWordViewModel @Inject constructor(var wordsRepository: WordsRepository,
                                           var hintsRepository: HintsRepository) : ViewModel() {

    fun getWordHints(word: String): Flowable<List<String>> {
        return hintsRepository.getHints(word).map {
            if (it.size > 5)
                it.subList(0, 5)
            else it
        }
    }

    fun saveWord(word: String, translation: String): Completable {
        return wordsRepository.addWord(Word(text = word, translation = translation))
    }
}