package com.appsoft.splyza.base.extensions

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun View.setVisibility(isVisible: Boolean): Unit =
    if (isVisible) visibility = View.VISIBLE else visibility = View.GONE

fun View.setVisibilityInvisible(isVisible: Boolean): Unit =
    if (isVisible) visibility = View.VISIBLE else visibility = View.INVISIBLE

fun Context.getColorFromRes(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getDrawableFromRes(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)