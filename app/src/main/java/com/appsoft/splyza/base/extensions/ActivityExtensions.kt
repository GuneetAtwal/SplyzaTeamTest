package com.appsoft.splyza.base.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.changeFragment(
    @IdRes containerViewId: Int,
    newFragment: Fragment,
    replaceFragment: Boolean = true,
    addToBackStack: Boolean = false
) {

    val transaction = supportFragmentManager.beginTransaction()

    if (replaceFragment) {
        transaction.replace(containerViewId, newFragment, newFragment.javaClass.simpleName)
    } else {
        transaction.add(containerViewId, newFragment, newFragment.javaClass.simpleName)
    }

    if (addToBackStack) {
        transaction.addToBackStack(newFragment.javaClass.simpleName)
    }

    transaction.commit()
}

fun Context.showLongToast(str: String) = Toast.makeText(this, str, Toast.LENGTH_LONG).show()

fun Context.showShortToast(str: String) = Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
