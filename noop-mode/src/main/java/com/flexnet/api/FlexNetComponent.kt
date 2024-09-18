package com.flexnet.api

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        Bind::class,
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
