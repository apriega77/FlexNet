package com.flexnet.domain.model

internal data class NetworkRule(
    val id: Int,
    val title: String,
    val isActive: Boolean,
    val url: String,
    val method: Method,
    val httpCode: Int,
    val responseBody: String,
) {
    companion object {
        val init =
            NetworkRule(
                id = 0,
                title = "",
                isActive = false,
                url = "",
                method = Method.GET,
                httpCode = 0,
                responseBody = "",
            )
    }
}
