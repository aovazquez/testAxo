package com.mx.testcore.core

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun AppCompatActivity.color(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Fragment.color(@ColorRes color: Int) = ContextCompat.getColor(requireActivity(), color)

fun Any?.isNull() = this == null

fun AppCompatActivity.toast(text:String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Fragment.toast(text:String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireActivity(), text, length).show()
}

fun ImageView.load(url:String) {
    if ( url.isNotEmpty() ) {
        Glide.with(this.context).load(url).into(this)
    }
}

fun EditText.onTextChanged(listener:(String) -> Unit ) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener(s.toString())
        }
        override fun afterTextChanged(s: Editable?) {}
    })
}

fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

// Command to start view logs in debug view
// adb shell setprop debug.firebase.analytics.app com.mx.uglysw.neighborhood

// Command to stop view logs in debug view
// adb shell setprop debug.firebase.analytics.app .none.