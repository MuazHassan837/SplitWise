package com.muazhassan.splitwise.Core.Profile.ui.home

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

import com.muazhassan.splitwise.Model.*

import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.*


class HomeViewModel : ViewModel() {


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    private val _email = MutableLiveData<String>()

    val email: LiveData<String>
        get() = _email


    private var _lendExpenses = MutableLiveData<RealmList<GroupExpense>>()

    val lendExpenses: LiveData<RealmList<GroupExpense>>
        get() = _lendExpenses

    private var _lendAmount = MutableLiveData<Double>()

    val lendAmount: LiveData<Double>
        get() = _lendAmount


    fun setEmail(email: String) {
        _email.value = email
    }


    fun launchAddExpenseActivity(fragment: Fragment) {
        val requestCode = 837
        val intent = Intent(fragment.requireContext(), AddExpenseActivity::class.java)
        fragment.startActivityForResult(intent, requestCode)
    }




}