package com.alexkazancew.billingsystemsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface WordsDAO {

    @Query("SELECT * FROM Words WHERE id = :id")
    fun getWordById(id: String): Flowable<Word>

    @Query("SELECT * FROM words")
    fun getAllWords(): Flowable<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: Word)

    @Query("DELETE FROM words")
    fun deleteAllWords()

    @Query("DELETE FROM words WHERE id=:id")
    fun deleteWord(id: String)
}