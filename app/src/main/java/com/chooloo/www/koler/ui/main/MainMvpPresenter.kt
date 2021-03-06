package com.chooloo.www.koler.ui.main

import android.content.Intent
import android.view.MenuItem
import android.view.MotionEvent
import com.chooloo.www.koler.ui.base.MvpPresenter

interface MainMvpPresenter<V : MainMvpView> : MvpPresenter<V> {
    fun onDialpadFabClick()
    fun onMenuClick()
    fun onOptionsItemSelected(item: MenuItem)
    fun onViewIntent(intent: Intent)
    fun onSearchTextChanged(text: String)
}