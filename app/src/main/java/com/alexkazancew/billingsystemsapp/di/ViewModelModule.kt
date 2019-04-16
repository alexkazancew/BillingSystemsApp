package com.alexkazancew.billingsystemsapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexkazancew.billingsystemsapp.BillingViewModelFactory
import com.alexkazancew.billingsystemsapp.features.addword.AddWordViewModel
import com.alexkazancew.billingsystemsapp.features.editword.EditWordViewModel
import com.alexkazancew.billingsystemsapp.features.wordslist.WordsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WordsListViewModel::class)
    abstract fun bindWordsViewModel(wordsListViewModel: WordsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddWordViewModel::class)
    abstract fun bindAddWordViewModel(addWordViewModel: AddWordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditWordViewModel::class)
    abstract fun bindRepoViewModel(editWordViewModel: EditWordViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: BillingViewModelFactory): ViewModelProvider.Factory
}