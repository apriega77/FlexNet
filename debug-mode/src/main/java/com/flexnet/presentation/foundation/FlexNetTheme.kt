package com.flexnet.presentation.foundation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
internal fun FlexNetTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = FlexNetColorScheme,
        typography = FlexNetTypography,
        shapes = FlexNetShape,
    ) {
        content()
    }
}
