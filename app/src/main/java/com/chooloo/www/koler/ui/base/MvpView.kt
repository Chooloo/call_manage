package com.chooloo.www.koler.ui.base

import androidx.annotation.StringRes

interface MvpView {
    fun onSetup()
    fun finish() {}

    fun hasPermission(permission: String): Boolean
    fun hasPermissions(permissions: Array<String>): Boolean

    fun askForPermission(permission: String, requestCode: Int?)
    fun askForPermissions(permissions: Array<String>, requestCode: Int?)

    fun showMessage(message: String)
    fun showMessage(@StringRes stringResId: Int)
    fun showError(message: String)
    fun showError(@StringRes stringResId: Int)

    fun getString(@StringRes resId: Int): String
}