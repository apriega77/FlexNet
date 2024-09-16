package com.flexnet.presentation.feature

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flexnet.presentation.base.ViewModelFactoryProvider
import com.flexnet.presentation.feature.add.AddRuleMainScreen
import com.flexnet.presentation.feature.list.NetworkRulesMainScreen
import com.flexnet.presentation.foundation.FlexNetTypography

@Composable
internal fun FlexNetMainScreen() {
    val context = LocalContext.current as FlexNetActivity
    val viewModel: FlexNetViewModel =
        viewModel(factory = ViewModelFactoryProvider(context.viewModelComponent.getViewModelFactory()))
    val navController = rememberNavController()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collect {
            when (it) {
                is FlexNetEffect.Navigation -> {
                    navController.navigate(it.flexNetNav.route)
                }

                is FlexNetEffect.PopBack -> {
                    navController.popBackStack(it.flexNetNav.route, false)
                }
            }
        }
    }
    val toolbarState = state.value.toolbarChange.invoke()
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(
                    text = toolbarState.title,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    style = FlexNetTypography.h6,
                )

                if (toolbarState.hasBackButton) {
                    IconButton(
                        onClick = { backPressedDispatcher?.onBackPressedDispatcher?.onBackPressed() },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(24.dp),
                    ) {
                        Image(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",

                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(24.dp),

                ) {
                    toolbarState.trailingIcon?.invoke()
                }
            }
        },
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = FlexNetNav.LIST.route,
        ) {
            composable(FlexNetNav.LIST.route) {
                NetworkRulesMainScreen {
                    viewModel.sendEvent(it)
                }
            }

            composable(FlexNetNav.ADD.route) {
                AddRuleMainScreen(state.value.networkRule) {
                    viewModel.sendEvent(it)
                }
            }
        }
    }
}
