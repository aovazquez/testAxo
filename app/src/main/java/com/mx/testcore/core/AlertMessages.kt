package com.mx.testcore.core

import android.content.Context
import com.mx.testcore.R
import com.mx.testcore.utils.Tools

object AlertMessages {

    fun showNetworkError(appContext: Context) {
        Tools.showAlertMessage(
            title = appContext.getString(R.string.common_error_connect_title),
            message = appContext.getString(R.string.common_error_connect),
            cxt = appContext,
        )
    }

    fun showCustom(title:String, message: String, appContext: Context) {
        Tools.showAlertMessage(
            title = title,
            message = message,
            cxt = appContext,
        )
    }

}