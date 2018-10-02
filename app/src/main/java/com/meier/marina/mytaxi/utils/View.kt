package com.meier.marina.mytaxi.utils

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast

fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, resId, duration).apply { show() }
}

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, text, duration).apply { show() }
}
