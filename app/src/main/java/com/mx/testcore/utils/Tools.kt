package com.mx.testcore.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mx.testcore.ui.components.ProgressDialogFragment
import java.text.DecimalFormat
import java.text.NumberFormat

class Tools {

    companion object {
        private const val TAG: String = "Tools"

        var progressDialog: ProgressDialogFragment? = null
        private var activity: AppCompatActivity? = null
        private var PROGRESS_TAG: String = "PROGRESS"
        private var alertCustom: AlertDialog.Builder? = null

        private val formatter: NumberFormat = DecimalFormat("$ #,###.##")

        /**
         * Function progressDialog
         * Displays a custom ProgressDialog with awesome animation :)
         * @param activity AppCompatActivity
         * @param cancelable Boolean
         * */
        fun progressDialog(cancelable: Boolean = true, activity: AppCompatActivity) {
            if (progressDialog == null) {
                progressDialog = ProgressDialogFragment()
                Companion.activity = activity
            }
            progressDialog!!.isCancelable = cancelable
            progressDialog!!.show(activity.supportFragmentManager, PROGRESS_TAG)
        }

        /**
         * Function dismissProgressDialog
         * Dismiss custom ProgressDialog already displayed
         * */
        fun dismissProgressDialog() {
            progressDialog?.let {
                it.dismiss()
                activity!!.supportFragmentManager.beginTransaction().remove(progressDialog!!).commit()
            }
            progressDialog = null
        }

        /**
         * Function showAlertMessage
         * Displays AlertDialog with custom title and message.
         * @param title String
         * @param message String
         * @param cancelable Boolean
         * @param cxt Context
         * */
        fun showAlertMessage(title: String, message: String, cancelable: Boolean = true, cxt: Context) {
            alertCustom = null
            alertCustom = AlertDialog.Builder(cxt).apply {
                setTitle(title)
                setMessage(message)
                setCancelable(cancelable)
                setPositiveButton("Aceptar") { _ , _ ->
                    alertCustom = null
                }
            }
            alertCustom!!.show()
        }

        fun moneyFormatter(value: Double): String {
            return formatter.format(value)
        }
    }
}