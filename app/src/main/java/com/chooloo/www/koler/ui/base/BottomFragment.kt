package com.chooloo.www.koler.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.chooloo.www.koler.databinding.FragmentBottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomFragment<FragmentType : BaseFragment>(
    val fragment: FragmentType
) : BottomSheetDialogFragment(), MvpView {

    private val _binding by lazy { FragmentBottomDialogBinding.inflate(layoutInflater) }
    private val _activity by lazy { context as BaseActivity }

    //region lifecycle
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is BaseActivity) {
            throw TypeCastException("Fragment not a child of base activity")
        }
        _activity.onAttachFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showsDialog = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = _binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetup()
    }

    override fun onSetup() {
        putFragment(fragment)
    }

    override fun finish() {
        dismiss()
    }
    //endregion

    //region permissions
    override fun hasPermission(permission: String) = false

    override fun hasPermissions(permissions: Array<String>) =
        permissions.any { p -> _activity.hasPermission(p) }

    override fun askForPermission(permission: String, requestCode: Int?) {
        askForPermissions(arrayOf(permission), requestCode)
    }

    override fun askForPermissions(permissions: Array<String>, requestCode: Int?) {
        requestPermissions(permissions, requestCode ?: 1)
    }
    //endregion

    //region messages
    override fun showMessage(message: String) {
        _activity.showMessage(message)
    }

    override fun showMessage(@StringRes stringResId: Int) {
        _activity.showMessage(stringResId)
    }

    override fun showError(message: String) {
        _activity.showError(message)
    }

    override fun showError(@StringRes stringResId: Int) {
        _activity.showError(getString(stringResId))
    }
    //endregion

    private fun putFragment(fragment: BaseFragment) {
        childFragmentManager.beginTransaction()
            .replace(_binding.bottomDialogFragmentPlaceholder.id, fragment).commit()
    }

    private val argsSafely: Bundle
        get() = arguments
            ?: throw IllegalArgumentException("Always create fragment with newInstance()")
}