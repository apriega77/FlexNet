package com.flexnet.api

import android.content.Context
import com.flexnet.presentation.feature.di.DaggerFlexNetComponent

object FlexNetLibraryFactory {
    fun create(context: Context, flexNetProperties: FlexNetProperties): FlexNetInterceptor {
        return DaggerFlexNetComponent.factory().create(context, flexNetProperties)
            .getFlexNetInterceptor()
    }
}
