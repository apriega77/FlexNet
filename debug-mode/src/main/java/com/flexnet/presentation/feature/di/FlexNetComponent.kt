package com.flexnet.presentation.feature.di

import android.content.Context
import com.flexnet.api.FlexNetProperties
import com.flexnet.data.di.Bind
import com.flexnet.data.di.Provide
import com.flexnet.data.interceptor.FlexNetInterceptorImpl
import com.flexnet.presentation.base.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        Bind::class,
        Provide::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
    ],
)
internal interface FlexNetComponent {
    fun getFlexNetInterceptor(): FlexNetInterceptorImpl

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance flexNetProperties: FlexNetProperties,
        ): FlexNetComponent
    }
}
