package com.alexkazancew.billingsystemsapp.data.cache

import androidx.collection.LruCache
import com.alexkazancew.billingsystemsapp.data.api.model.TranslateResponse

class TranslationApiCache (maxSize: Int = 10) : LruCache<String, TranslateResponse>(maxSize) {

    fun addResponse(word: String, response: TranslateResponse) {
        if (this[word] != null) {
            return
        }

        this.put(word, response)
    }

    fun getResponse(word: String): TranslateResponse? {
        return this.get(word)
    }
}