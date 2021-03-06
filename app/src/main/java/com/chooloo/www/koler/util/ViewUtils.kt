package com.chooloo.www.koler.util

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat


fun Context.hasNavBar(): Boolean {
    val id = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    return id > 0 && resources.getBoolean(id)
}

fun Context.navBarHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
}

@ColorInt
fun Context.getAttrColor(@AttrRes attrRes: Int): Int {
    return TypedValue().also { theme.resolveAttribute(attrRes, it, true) }.data
}

fun Context.getSelectableItemBackgroundDrawable() = ContextCompat.getDrawable(
    this,
    TypedValue().also {
        theme.resolveAttribute(android.R.attr.selectableItemBackground, it, true)
    }.resourceId
)

fun Context.getSelectableItemBackgroundBorderlessDrawable() = ContextCompat.getDrawable(
    this,
    TypedValue().also {
        theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, it, true)
    }.resourceId
)

fun Context.sizeInDp(sizeInDp: Int) = (sizeInDp * resources.displayMetrics.density + 0.5f).toInt()
