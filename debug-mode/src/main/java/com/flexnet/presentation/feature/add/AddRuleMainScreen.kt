package com.flexnet.presentation.feature.add

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flexnet.R
import com.flexnet.domain.model.NetworkRule
import com.flexnet.presentation.base.ViewModelFactoryProvider
import com.flexnet.presentation.feature.FlexNetActivity
import com.flexnet.presentation.feature.FlexNetEvent
import com.flexnet.presentation.feature.FlexNetNav
import com.flexnet.presentation.feature.toolbar.FlexNetToolbarState
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun AddRuleMainScreen(networkRule: NetworkRule?, flexNetEvent: (FlexNetEvent) -> Unit) {
    val context = LocalContext.current as FlexNetActivity
    val viewModel: AddRuleViewModel =
        viewModel(factory = ViewModelFactoryProvider(context.viewModelComponent.getViewModelFactory()))
    val state = viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val activity = LocalContext.current as FlexNetActivity

    val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipboardHasPrimaryClip = remember {
        clipboard.hasPrimaryClip()
    }

    LaunchedEffect(key1 = Unit) {
        flexNetEvent.invoke(
            FlexNetEvent.ToolbarChange {
                FlexNetToolbarState.AddRule {
                    if (clipboardHasPrimaryClip) {
                        IconButton(
                            onClick = {
                                viewModel.sendEvent(
                                    AddRuleEvent.PasteClipboard(
                                        clipboard,
                                    ),
                                )
                            },
                            modifier = Modifier.size(32.dp),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_paste),
                                contentDescription = "paste",
                            )
                        }
                    }
                }
            },
        )
        viewModel.sendEvent(AddRuleEvent.SetArgs(networkRule))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect {
            when (it) {
                AddRuleEffect.PopBackToNetworkRulesScreen -> flexNetEvent.invoke(
                    FlexNetEvent.PopBack(
                        FlexNetNav.LIST,
                    ),
                )

                is AddRuleEffect.ShowSnackBar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(it.text)
                    }
                }
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {
        AddRuleScreen(state = state.value) {
            viewModel.sendEvent(it)
        }
    }
}
