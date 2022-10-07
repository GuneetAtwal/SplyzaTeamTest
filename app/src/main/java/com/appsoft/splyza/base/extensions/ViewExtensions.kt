package com.appsoft.splyza.base.extensions

import android.view.View

fun View.setVisibility(isVisible: Boolean): Unit =
    if (isVisible) visibility = View.VISIBLE else visibility = View.GONE

fun View.setVisibilityInvisible(isVisible: Boolean): Unit =
    if (isVisible) visibility = View.VISIBLE else visibility = View.INVISIBLE