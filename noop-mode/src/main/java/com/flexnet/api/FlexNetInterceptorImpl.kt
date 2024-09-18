package com.flexnet.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class FlexNetInterceptorImpl @Inject constructor() :
    FlexNetInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request)
    }
}
