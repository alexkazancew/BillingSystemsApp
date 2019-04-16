package com.alexkazancew.billingsystemsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexkazancew.billingsystemsapp.data.repositories.HintsRepository
import com.alexkazancew.billingsystemsapp.data.repositories.WordsRepository
import com.alexkazancew.billingsystemsapp.features.addword.AddWordViewModel
import com.alexkazancew.billingsystemsapp.features.editword.EditWordViewModel
import com.alexkazancew.billingsystemsapp.features.wordslist.WordsListViewModel
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class BillingViewModelFactory @Inject constructor(
        private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
