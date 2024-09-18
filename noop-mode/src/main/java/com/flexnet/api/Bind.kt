package com.flexnet.api

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class Bind {

    @Binds
    @Singleton
    abstract fun bindFlexNetInterceptorImpl(interceptor: FlexNetInterceptorImpl): FlexNetInterceptor
}
