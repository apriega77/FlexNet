package com.flexnet.presentation.feature.di

import androidx.lifecycle.ViewModel
import com.flexnet.presentation.base.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
internal object ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(
        viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
    ): ViewModelFactory {
        return ViewModelFactory(viewModelMap)
    }
}
