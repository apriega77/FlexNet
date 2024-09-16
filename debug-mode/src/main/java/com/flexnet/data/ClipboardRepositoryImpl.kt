package com.flexnet.data

import com.flexnet.domain.model.NetworkRule
import com.flexnet.domain.repository.ClipboardRepository
import javax.inject.Inject

internal class ClipboardRepositoryImpl @Inject constructor(private val networkRuleMapper: NetworkRuleMapper) :
    ClipboardRepository {
    override fun getDataFromClipboard(string: String): NetworkRule? {
        val urlPattern = "URL: (.*)".toRegex()
        val url = urlPattern.find(string)?.groupValues?.get(1)?.trim() ?: return null

        val methodPattern = "Method: (.*)".toRegex()
        val methodResult = methodPattern.find(string)?.groupValues?.get(1)?.trim() ?: ""
        val method = networkRuleMapper.mapMethod(methodResult)

        val responseCodePattern = "Response: (\\d+) ".toRegex()
        val responseCode = responseCodePattern.find(string)?.groupValues?.get(1)?.toInt() ?: 200

        val responseSection = string.split("---------- Response ----------").getOrNull(1) ?: ""
        val responseBodyRegex = """\{(?:[^{}"]|"(?:\\.|[^"])*")*\}""".toRegex()
        val responseBody =
            responseBodyRegex.find(responseSection)?.value ?: ""

        return NetworkRule(
            id = 0,
            title = "",
            isActive = true,
            url = url,
            method = method,
            httpCode = responseCode,
            responseBody = responseBody,
        )
    }
}
