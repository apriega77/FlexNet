package com.flexnet.data.di

import com.flexnet.api.FlexNetInterceptor
import com.flexnet.data.ClipboardRepositoryImpl
import com.flexnet.data.NetworkRuleRepositoryImpl
import com.flexnet.data.interceptor.FlexNetInterceptorImpl
import com.flexnet.domain.repository.ClipboardRepository
import com.flexnet.domain.repository.NetworkRuleRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class Bind {
    @Binds
    @Singleton
    abstract fun bindRepo(
        flexNetRepositoryImpl: NetworkRuleRepositoryImpl,
    ): NetworkRuleRepository

    @Binds
    @Singleton
    abstract fun bindFlexNetInterceptorImpl(interceptor: FlexNetInterceptorImpl): FlexNetInterceptor

    @Binds
    @Singleton
    abstract fun bindClipboardRepo(clipboardRepositoryImpl: ClipboardRepositoryImpl): ClipboardRepository
}
