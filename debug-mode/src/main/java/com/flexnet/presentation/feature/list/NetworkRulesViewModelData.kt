package com.flexnet.presentation.feature.list

import com.flexnet.domain.model.NetworkRule

internal data class NetworkRulesState(val networkRules: List<NetworkRule> = emptyList())

internal sealed interface NetworkRulesEvent {
    object GetNetworkRules : NetworkRulesEvent
    data class NetworkRuleToggle(val isActive: Boolean, val networkRule: NetworkRule) :
        NetworkRulesEvent

    data class NavigateToAddRule(val networkRule: NetworkRule? = null) : NetworkRulesEvent
}

internal sealed interface NetworkRulesEffect {
    data class NavigateToAddRule(val networkRule: NetworkRule? = null) : NetworkRulesEffect
}
