package com.muazhassan.splitwise.Core.Profile.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muazhassan.splitwise.Model.GroupExpense
import io.realm.kotlin.types.RealmList

class GalleryViewModel : ViewModel() {


    private var _ownedExpenses = MutableLiveData<RealmList<GroupExpense>>()

    val ownedExpenses: LiveData<RealmList<GroupExpense>>
        get() = _ownedExpenses

    private var _ownedAmount = MutableLiveData<Double>()

    val ownedAmount: LiveData<Double>
        get() = _ownedAmount



}