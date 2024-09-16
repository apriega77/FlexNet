package com.flexnet.presentation.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.flexnet.presentation.feature.di.DaggerViewModelComponent
import com.flexnet.presentation.feature.di.ViewModelComponent
import com.flexnet.presentation.foundation.FlexNetTheme

internal class FlexNetActivity : ComponentActivity() {

    internal lateinit var viewModelComponent: ViewModelComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelComponent = DaggerViewModelComponent.factory().create(this)
        setContent {
            FlexNetTheme {
                FlexNetMainScreen()
            }
        }
    }
}
