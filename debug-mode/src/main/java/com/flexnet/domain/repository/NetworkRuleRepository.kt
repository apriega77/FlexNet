package com.flexnet.domain.repository

import com.flexnet.domain.model.NetworkRule

internal interface NetworkRuleRepository {
    suspend fun getRule(): List<NetworkRule>
    suspend fun deleteRule(args: NetworkRule)
    suspend fun saveRule(args: NetworkRule)
}
