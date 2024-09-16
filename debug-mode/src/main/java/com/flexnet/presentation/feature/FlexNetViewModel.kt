package com.flexnet.presentation.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FlexNetViewModel @Inject constructor() :
    ViewModel() {

    private val _state = MutableStateFlow(FlexNetState())
    val state: StateFlow<FlexNetState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<FlexNetEvent>()
    val event = _event.asSharedFlow()

    val effect = MutableSharedFlow<FlexNetEffect>()

    init {
        _event.onEach {
            mapEvent(it)
        }.launchIn(viewModelScope)
    }

    fun sendEvent(event: FlexNetEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    fun mapEvent(event: FlexNetEvent) {
        viewModelScope.launch {
            when (event) {
                is FlexNetEvent.Navigation -> {
                    effect.emit(FlexNetEffect.Navigation(event.flexNetNav))
                }

                is FlexNetEvent.ToolbarChange -> {
                    _state.update {
                        it.copy(toolbarChange = event.toolbarChange)
                    }
                }

                is FlexNetEvent.SetNetworkRule -> {
                    _state.update {
                        it.copy(networkRule = event.networkRule)
                    }
                }

                is FlexNetEvent.PopBack -> {
                    effect.emit(FlexNetEffect.PopBack(event.flexNetNav))
                }
            }
        }
    }
}
