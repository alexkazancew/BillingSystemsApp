package com.alexkazancew.billingsystemsapp.di

import com.alexkazancew.billingsystemsapp.features.addword.AddWordFragment
import com.alexkazancew.billingsystemsapp.features.editword.EditWordFragment
import com.alexkazancew.billingsystemsapp.features.wordslist.WordsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun wordsListFragment(): WordsListFragment

    @ContributesAndroidInjector
    abstract fun editWordFragment(): EditWordFragment

    @ContributesAndroidInjector
    abstract fun addWordFragment(): AddWordFragment
}