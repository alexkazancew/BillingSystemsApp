package com.alexkazancew.billingsystemsapp.data.repositories

import com.alexkazancew.billingsystemsapp.data.api.DictionaryApiService
import com.alexkazancew.billingsystemsapp.data.cache.TranslationApiCache
import com.alexkazancew.billingsystemsapp.data.mappers.Response2HintsConverter
import io.reactivex.Flowable

class HintsRepository(
        private val cache: TranslationApiCache,
        private var api: DictionaryApiService,
        private var mapper: Response2HintsConverter) {

    private var defaultLangs = "ru-en"

    fun getHints(word: String): Flowable<List<String>> {
        val fromCache = cache.getResponse(word)
        return if (fromCache != null) {
            Flowable.fromCallable { mapper.convert(fromCache) }
        } else {
            val fromApi = api.translateText(lang = defaultLangs, text = word)
                    .map {
                        if (it.defenition.size > 0) {
                            cache.addResponse(word, it)
                            mapper.convert(it)
                        } else {
                            listOf<String>()
                        }
                    }

            fromApi
        }
    }
}