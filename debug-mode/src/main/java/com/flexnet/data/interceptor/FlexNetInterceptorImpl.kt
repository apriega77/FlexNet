package com.flexnet.data.interceptor

import com.flexnet.api.FlexNetInterceptor
import com.flexnet.domain.model.NetworkRule
import com.flexnet.domain.repository.NetworkRuleRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

internal class FlexNetInterceptorImpl @Inject constructor(private val networkRuleRepository: NetworkRuleRepository) :
    FlexNetInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalResponse = chain.proceed(request)

        val url = request.url.toString()
        val method = request.method
        val rules = runBlocking { networkRuleRepository.getRule() }
        val matchedRule = getMatchingRule(rules, url, method)
        return matchedRule?.let {
            originalResponse.close()
            val modifiedResponse = originalResponse.newBuilder()
                .code(it.httpCode)
                .body(it.responseBody.toResponseBody("application/json".toMediaType()))
                .build()

            modifiedResponse
        } ?: originalResponse
    }

    private fun getMatchingRule(
        list: List<NetworkRule>,
        url: String,
        method: String,
    ): NetworkRule? {
        return list.find {
            url.contains(it.url) && method.contains(it.method.name) && it.isActive && it.responseBody.isNotBlank()
        }
    }
}
