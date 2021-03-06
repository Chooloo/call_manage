package com.chooloo.www.koler.ui.list

import com.chooloo.www.koler.ui.base.MvpView

interface ListMvpView : MvpView {
    val itemCount: Int

    fun showEmptyPage(isShow: Boolean)
    fun showPermissionsPage(isShow: Boolean)
    fun animateListView()
}