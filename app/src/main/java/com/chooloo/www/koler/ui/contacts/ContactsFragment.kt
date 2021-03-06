package com.chooloo.www.koler.ui.contacts

import androidx.lifecycle.ViewModelProvider
import com.chooloo.www.koler.adapter.ContactsAdapter
import com.chooloo.www.koler.data.Contact
import com.chooloo.www.koler.data.ContactsBundle
import com.chooloo.www.koler.livedata.ContactsProviderLiveData
import com.chooloo.www.koler.ui.base.BottomFragment
import com.chooloo.www.koler.ui.contact.ContactFragment
import com.chooloo.www.koler.ui.list.ListFragment
import com.chooloo.www.koler.util.permissions.runWithPermissions
import com.chooloo.www.koler.viewmodel.SearchViewModel

class ContactsFragment : ListFragment<ContactsAdapter>(), ContactsMvpView {

    companion object {
        fun newInstance() = ContactsFragment()
    }

    private val _searchViewModel by lazy { ViewModelProvider(requireActivity()).get(SearchViewModel::class.java) }
    private val _contactsLiveData by lazy { ContactsProviderLiveData(_activity) }
    private val _presenter: ContactsMvpPresenter<ContactsMvpView> by lazy { ContactsPresenter() }

    override fun onGetAdapter() = ContactsAdapter().apply {
        setOnItemClickListener(_presenter::onContactItemClick)
        setOnItemLongClickListener(_presenter::onContactItemLongClick)
    }

    override fun onSetup() {
        super.onSetup()

        _presenter.attach(this)

        showEmptyPage(false)
        showPermissionsPage(false)
        observe()
    }

    override fun onDestroy() {
        super.onDestroy()
        _presenter.detach()
    }

    override fun observe() = runWithPermissions(
        permissions = _contactsLiveData.requiredPermissions,
        grantedCallback = {
            _contactsLiveData.observe(viewLifecycleOwner, _presenter::onContactsChanged)
            _searchViewModel.apply {
                number.observe(viewLifecycleOwner, _presenter::onDialpadNumberChanged)
                text.observe(viewLifecycleOwner, _presenter::onSearchTextChanged)
            }
            showPermissionsPage(false)
        },
        blockedCallback = _presenter::onPermissionsBlocked
    )

    override fun openContact(contact: Contact) {
        BottomFragment(ContactFragment.newInstance(contact.id)).show(
            _activity.supportFragmentManager,
            ContactFragment.TAG
        )
    }

    override fun updateContacts(contactsBundle: ContactsBundle) {
        listAdapter.data = contactsBundle.listBundleByLettersWithFavs
    }

    override fun setContactsFilter(filter: String?) {
        _contactsLiveData.setFilter(filter)
    }
}