package com.flexnet.presentation.feature

import com.flexnet.domain.model.NetworkRule
import com.flexnet.presentation.feature.toolbar.FlexNetToolbarState

internal data class FlexNetState(
    val toolbarChange: () -> FlexNetToolbarState = { FlexNetToolbarState.ListScreen },
    val networkRule: NetworkRule? = null,
)

internal sealed interface FlexNetEvent {
    data class ToolbarChange(val toolbarChange: () -> FlexNetToolbarState) : FlexNetEvent
    data class Navigation(val flexNetNav: FlexNetNav) : FlexNetEvent
    data class PopBack(val flexNetNav: FlexNetNav) : FlexNetEvent
    data class SetNetworkRule(val networkRule: NetworkRule?) : FlexNetEvent
}

internal sealed interface FlexNetEffect {
    data class Navigation(val flexNetNav: FlexNetNav) : FlexNetEffect
    data class PopBack(val flexNetNav: FlexNetNav) : FlexNetEffect
}
