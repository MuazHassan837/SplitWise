package com.muazhassan.splitwise.Core.Profile.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muazhassan.splitwise.Model.Group
import com.muazhassan.splitwise.Model.GroupExpense
import com.muazhassan.splitwise.Model.GroupMembership
import com.muazhassan.splitwise.Model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList

class GalleryViewModel : ViewModel() {

    val realm: Realm by lazy {
        val config = RealmConfiguration.create(
            schema = setOf(
                Group::class,
                GroupExpense::class,
                GroupMembership::class,
                User::class
            )
        )
        Realm.open(config)
    }

    private var _ownedExpenses = MutableLiveData<RealmList<GroupExpense>>()

    val ownedExpenses: LiveData<RealmList<GroupExpense>>
        get() = _ownedExpenses

    private var _ownedAmount = MutableLiveData<Double>()

    val ownedAmount: LiveData<Double>
        get() = _ownedAmount


    fun reloadContent(comingEmail: String) {
        val filteredExpenses = realmListOf<GroupExpense>()
        val allGroupImPartOf =
            realm.query(clazz = GroupMembership::class, "userEmail == $0", comingEmail).find()

        for (eachGroup in allGroupImPartOf) {
            val groupFetched =
                realm.query(clazz = Group::class, "name == $0", eachGroup.groupName).find().first()
            filteredExpenses.addAll(groupFetched.groupExpenses.filter { it.email == comingEmail })
        }

        _ownedExpenses.value = filteredExpenses
        _ownedAmount.value = filteredExpenses.sumByDouble { it.amount }
    }


}