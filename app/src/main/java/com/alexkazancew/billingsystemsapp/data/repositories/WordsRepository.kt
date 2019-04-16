package com.alexkazancew.billingsystemsapp.data.repositories

import com.alexkazancew.billingsystemsapp.data.db.Word
import com.alexkazancew.billingsystemsapp.data.db.WordsDAO
import io.reactivex.Completable
import io.reactivex.Flowable

class WordsRepository(private var wordsDao: WordsDAO) {

    fun getWords(): Flowable<List<Word>> {
        return wordsDao.getAllWords()
    }

    fun deleteWordById(wordId: String): Completable {
        return Completable.fromAction { wordsDao.deleteWord(wordId) }
    }

    fun addWord(word: Word): Completable {
        return Completable.fromAction { wordsDao.insertWord(word) }
    }

    fun getWord(word: String): Flowable<Word> {
        return wordsDao.getWordById(word)
    }
}