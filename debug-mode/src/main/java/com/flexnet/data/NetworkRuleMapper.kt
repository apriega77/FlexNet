package com.flexnet.data

import com.flexnet.data.room.entity.NetworkRuleEntity
import com.flexnet.domain.model.Method
import com.flexnet.domain.model.NetworkRule
import javax.inject.Inject

internal class NetworkRuleMapper @Inject constructor() {

    fun mapNetworkRule(networkRuleEntity: NetworkRuleEntity): NetworkRule {
        return NetworkRule(
            id = networkRuleEntity.id,
            title = networkRuleEntity.title.orEmpty(),
            isActive = networkRuleEntity.isActive ?: false,
            url = networkRuleEntity.url.orEmpty(),
            method = mapMethod(networkRuleEntity.method.orEmpty()),
            httpCode = networkRuleEntity.httpCode ?: 200,
            responseBody = networkRuleEntity.responseBody.orEmpty(),
        )
    }

    fun mapNetworkRuleEntity(networkRule: NetworkRule): NetworkRuleEntity {
        return NetworkRuleEntity(
            id = networkRule.id,
            title = networkRule.title,
            isActive = networkRule.isActive,
            url = networkRule.url,
            method = networkRule.method.name,
            httpCode = networkRule.httpCode,
            responseBody = networkRule.responseBody,
        )
    }

    fun mapMethod(args: String): Method {
        return Method.values().firstOrNull {
            it.name.contains(args)
        } ?: Method.GET
    }
}
