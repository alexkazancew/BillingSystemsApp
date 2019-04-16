package com.alexkazancew.billingsystemsapp.data.mappers

import com.alexkazancew.billingsystemsapp.data.api.model.TranslateResponse

class Response2HintsConverter {

    fun convert(response: TranslateResponse): List<String> {
        val translationHints = mutableListOf<String>()

        response.defenition.forEach { def ->
            def.translation.forEach {
                translationHints.add(it.text)
            }
        }
        return translationHints
    }
}