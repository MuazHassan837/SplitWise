package com.muazhassan.splitwise.Core.Profile.ui.home

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.muazhassan.splitwise.Core.Service.FirestoreManager
import com.muazhassan.splitwise.Core.Service.RealmManager
import com.muazhassan.splitwise.Model.*
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

class HomeViewModel : ViewModel() {


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
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    private val localServices = RealmManager()
    private val remoteServices = FirestoreManager()


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

    suspend fun saveUser(comingEmail: String) {
        localServices.saveUserLocalDb(comingEmail)
    }

    suspend fun removeExpenseWithId(comingId: String, comingGroup: String) {
        localServices.removeExpenseLocalDb(comingId)
        remoteServices.removeGroupExpenseRemoteDb(comingId, comingGroup)
    }

    suspend fun saveGroup(comingGroup: String) {
        localServices.saveGroupLocalDb(comingGroup)
        remoteServices.saveGroupRemoteDb(comingGroup)
    }

    suspend fun saveGroupRelation(comingGroupName: String, comingEmail: String) {
        localServices.saveGroupMemberRelationLocalDb(comingGroupName, comingEmail)
        remoteServices.saveGroupMemberRelationsRemoteDb(comingGroupName, comingEmail)
    }

    fun launchAddExpenseActivity(fragment: Fragment) {
        val requestCode = 837
        val intent = Intent(fragment.requireContext(), AddExpenseActivity::class.java)
        fragment.startActivityForResult(intent, requestCode)
    }

    suspend fun saveLendExpenseWith(
        comingAmount: Double,
        comingEmail: String,
        strategy: SplitStrategy,
        description: String,
        groupName: String,
        splitValue: Int
    ) {

        val expenseAmount = BigDecimal(comingAmount)
        val numberOfMembers =
            realm.query(clazz = GroupMembership::class, "groupName == $0", groupName).find().size

        val memberSplit = if (numberOfMembers - 1 > 1) numberOfMembers - 1 else 1

        val amountPerMember = when (strategy) {
            SplitStrategy.EQUAL -> expenseAmount.divide(
                BigDecimal(numberOfMembers),
                2,
                RoundingMode.HALF_EVEN
            ).toDouble()
            SplitStrategy.EQUAL_MINUS -> expenseAmount.divide(
                BigDecimal(memberSplit),
                2,
                RoundingMode.HALF_EVEN
            ).toDouble()
            SplitStrategy.PERCENTAGE -> {
                val remainingAmount = expenseAmount.subtract(expenseAmount.multiply(BigDecimal(100 - splitValue).divide(BigDecimal(100), 2, RoundingMode.HALF_EVEN)))
                remainingAmount.divide(BigDecimal(memberSplit), 2, RoundingMode.HALF_EVEN).toDouble()
            }
            null -> throw IllegalArgumentException("Invalid split strategy")
        }


        val groupMembers =
            realm.query(clazz = GroupMembership::class, "groupName == $0", groupName).find()

        groupMembers.forEach { membership ->
            val expense = GroupExpense(
                description = description ?: "",
                amount = amountPerMember,
                date = LocalDate.now(),
                payerEmail = comingEmail,
                email = membership.userEmail,
                groupName = groupName
            )
            if (membership.groupName == groupName && membership.userEmail != comingEmail) {
                realm.writeBlocking {
                    val existingGroup =
                        query(clazz = Group::class, "name == $0", groupName).find().first().apply {
                            groupExpenses.add(expense)
                        }
                }
                remoteServices.updateGroupExpenseRemoteDb(expense)
            }
        }
        this.reloadContent(comingEmail)
    }

    fun reloadContent(comingEmail: String) {
        val filteredExpenses = realmListOf<GroupExpense>()
        val allGroupImPartOf =
            realm.query(clazz = GroupMembership::class, "userEmail == $0", comingEmail).find()

        for (eachGroup in allGroupImPartOf) {
            val groupFetched =
                realm.query(clazz = Group::class, "name == $0", eachGroup.groupName).find().first()
            filteredExpenses.addAll(groupFetched.groupExpenses.filter { it.payerEmail == comingEmail })
        }

        _lendExpenses.value = filteredExpenses
        _lendAmount.value = filteredExpenses.sumByDouble { it.amount }
    }

    fun fetchingGroups() = GlobalScope.launch {
        val fetchedGroup = withContext(Dispatchers.IO) {
            remoteServices.loadGroup().await().toMutableList()
        }
        realm.write {
            fetchedGroup.forEach {
                copyToRealm(it)
            }
        }
    }

    fun fetchingGroupsMembers() = GlobalScope.launch {
        val fetchedGroupMembers = withContext(Dispatchers.IO) {
            remoteServices.loadGroupMembers().await().toMutableList()
        }
        realm.write {
            fetchedGroupMembers.forEach {
                copyToRealm(it)
            }
        }
    }

    fun reloadFetched(comingEmail: String) {
        viewModelScope.launch {
            this@HomeViewModel.reloadContent(comingEmail)
        }
    }

    suspend fun loadFromCloud(comingEmail: String) {
        val groups = realm.query(clazz = Group::class).find()
        if (groups.size == 0) {
            fetchingGroups().join()
            fetchingGroupsMembers().join()
            reloadFetched(comingEmail)
        }
    }


}