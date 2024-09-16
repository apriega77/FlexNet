package com.flexnet.domain.repository

import com.flexnet.domain.model.NetworkRule

internal interface ClipboardRepository {
    fun getDataFromClipboard(string: String): NetworkRule?
}
