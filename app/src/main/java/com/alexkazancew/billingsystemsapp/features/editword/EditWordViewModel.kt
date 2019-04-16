package com.alexkazancew.billingsystemsapp.features.editword

import androidx.lifecycle.ViewModel
import com.alexkazancew.billingsystemsapp.data.db.Word
import com.alexkazancew.billingsystemsapp.data.repositories.HintsRepository
import com.alexkazancew.billingsystemsapp.data.repositories.WordsRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject


class EditWordViewModel @Inject constructor(
        var hintsRepository: HintsRepository,
        var wordsRepository: WordsRepository
) : ViewModel() {

    fun getHints(word: String): Flowable<List<String>> {
        return hintsRepository.getHints(word).map {
            if (it.size > 5) {
                it.subList(0, 5)
            } else it
        }
    }

    fun saveWord(word: Word): Completable {
        return wordsRepository.addWord(word)
    }

    fun getWord(word: String): Flowable<Word> {
        return wordsRepository.getWord(word)
    }

    fun deleteWord(wordId: String): Completable {
        return wordsRepository.deleteWordById(wordId)
    }
}