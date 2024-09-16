package com.flexnet.presentation.feature.add

import android.content.ClipboardManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexnet.domain.model.NetworkRule
import com.flexnet.domain.repository.ClipboardRepository
import com.flexnet.domain.repository.NetworkRuleRepository
import com.flexnet.presentation.feature.toIntOrZero
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

internal class AddRuleViewModel @Inject constructor(
    private val networkRuleRepository: NetworkRuleRepository,
    private val clipboardRepository: ClipboardRepository,
) :
    ViewModel() {

    private val _state = MutableStateFlow<AddRuleState>(AddRuleState.Add())
    val state: StateFlow<AddRuleState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<AddRuleEvent>()
    val event = _event.asSharedFlow()

    val effect = MutableSharedFlow<AddRuleEffect>()

    init {
        _event.onEach {
            mapEvent(it, state.value)
        }.launchIn(viewModelScope)
    }

    fun sendEvent(event: AddRuleEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    private fun mapEvent(event: AddRuleEvent, lastState: AddRuleState) {
        viewModelScope.launch {
            when (event) {
                is AddRuleEvent.OnTitleChange -> {
                    _state.update {
                        it.copyValue(titleState = event.text)
                    }
                }

                is AddRuleEvent.OnToggleChange -> {
                    _state.update {
                        it.copyValue(isActiveState = event.isActive)
                    }
                }

                is AddRuleEvent.OnUrlChange -> {
                    _state.update {
                        it.copyValue(urlState = event.text)
                    }
                }

                is AddRuleEvent.OnMethodItemChange -> {
                    _state.update {
                        it.copyValue(methodState = event.method)
                    }
                }

                is AddRuleEvent.OnHttpCodeChange -> {
                    _state.update {
                        it.copyValue(httpCodeState = event.text)
                    }
                }

                is AddRuleEvent.OnResponseBodyChange -> {
                    _state.update {
                        it.copyValue(responseBodyState = event.text)
                    }
                }

                is AddRuleEvent.SetArgs -> {
                    setArgs(event)
                }

                AddRuleEvent.DeleteRule -> {
                    deleteRule()
                }

                AddRuleEvent.SaveRule -> {
                    saveRule()
                }

                is AddRuleEvent.PasteClipboard -> {
                    processClipboard(event.clipboard, lastState)
                }
            }
        }
    }

    private fun setArgs(event: AddRuleEvent.SetArgs) {
        if (event.networkRule != null) {
            _state.value = AddRuleState.Detail(
                idState = event.networkRule.id,
                titleState = event.networkRule.title,
                isActiveState = event.networkRule.isActive,
                urlState = event.networkRule.url,
                methodState = event.networkRule.method,
                httpCodeState = event.networkRule.httpCode.toString(),
                responseBodyState = event.networkRule.responseBody,
            )
        }
    }

    private fun saveRule() {
        viewModelScope.launch {
            networkRuleRepository.saveRule(
                NetworkRule(
                    id = state.value.idState,
                    title = state.value.titleState,
                    isActive = state.value.isActiveState,
                    url = state.value.urlState,
                    method = state.value.methodState,
                    httpCode = state.value.httpCodeState.toIntOrZero(),
                    responseBody = state.value.responseBodyState,
                ),
            )
            if (state.value is AddRuleState.Add) {
                effect.emit(AddRuleEffect.PopBackToNetworkRulesScreen)
            } else {
                effect.emit(AddRuleEffect.ShowSnackBar("Changes Saved"))
            }
        }
    }

    private fun deleteRule() {
        viewModelScope.launch {
            if (state.value is AddRuleState.Detail) {
                state.value.let { networkRuleRepository.deleteRule((state.value as AddRuleState.Detail).toNetworkRule()) }
                effect.emit(AddRuleEffect.PopBackToNetworkRulesScreen)
            }
        }
    }

    private fun processClipboard(clipboardManager: ClipboardManager, lastState: AddRuleState) {
        val clipText = clipboardManager.primaryClip?.getItemAt(0)?.text.toString()
        val networkRule = clipboardRepository.getDataFromClipboard(clipText)

        if (networkRule != null) {
            _state.value = lastState.copyValue(
                titleState = lastState.titleState,
                isActiveState = lastState.isActiveState,
                urlState = networkRule.url,
                methodState = networkRule.method,
                httpCodeState = networkRule.httpCode.toString(),
                responseBodyState = networkRule.responseBody,
            )
        } else {
            viewModelScope.launch {
                effect.emit(AddRuleEffect.ShowSnackBar("Data Not Valid"))
            }
        }
    }

    private fun AddRuleState.Detail.toNetworkRule(): NetworkRule {
        return NetworkRule(
            id = idState,
            title = titleState,
            isActive = isActiveState,
            url = urlState,
            method = methodState,
            httpCode = httpCodeState.toIntOrZero(),
            responseBody = responseBodyState,
        )
    }
}
