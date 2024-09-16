package com.flexnet.presentation.base

import androidx.lifecycle.ViewModelProvider

internal class ViewModelFactoryProvider(private val factory: ViewModelProvider.Factory) :
    ViewModelProvider.Factory by factory
