package com.flexnet.api

import android.content.Context
import android.content.Intent
import com.flexnet.presentation.feature.FlexNetActivity

object FlexNet {
    fun goToFlexNetActivity(context: Context) {
        val intent = Intent(context, FlexNetActivity::class.java)
        context.startActivity(intent)
    }
}
