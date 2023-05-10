package com.muazhassan.splitwise.Core.Service

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muazhassan.splitwise.Model.*
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import timber.log.Timber
import java.time.LocalDate
import java.util.Collections.addAll

class FirestoreManager {

    val db = Firebase.firestore

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

    suspend fun saveGroupRemoteDb(comingGroup: String) {
        var group = FbGroup(comingGroup)

        db.collection("groups")
            .whereEqualTo("name", comingGroup)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    db.collection("groups")
                        .add(group)
                        .addOnSuccessListener { documentReference ->
                            Timber.i("Group document written with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Timber.i("Error adding group document ${e.message}")
                        }
                } else {
                    Timber.i("Group already exists")
                }
            }
            .addOnFailureListener { e ->
                Timber.i("Error checking if group exists${e.message}")
            }
    }

    suspend fun saveGroupMemberRelationsRemoteDb(comingGroupName: String, comingEmail: String) {
        var groupMembership = FbGroupMembership(comingGroupName, comingEmail)

        db.collection("groupMemberships")
            .whereEqualTo("groupName", comingGroupName)
            .whereEqualTo("userEmail", comingEmail)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    db.collection("groupMemberships")
                        .add(groupMembership)
                        .addOnSuccessListener { documentReference ->
                            Timber.i("Group membership added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Timber.i("Error adding group member ${e.message}")
                        }
                } else {
                    Timber.i("Group relation already exists")
                }
            }
            .addOnFailureListener { e ->
                Timber.i("Error checking if it exists${e.message}")
            }
    }


    suspend fun updateGroupExpenseRemoteDb(comingExpense: GroupExpense) {
        val query = db.collection("groups")
            .whereEqualTo("name", comingExpense.groupName)

        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val groupDoc = querySnapshot.documents[0]
                val fbGroupExpense = FbGroupExpense(
                    id = comingExpense.id,
                    description = comingExpense.description,
                    amount = comingExpense.amount,
                    date = LocalDate.now(),
                    payerEmail = comingExpense.payerEmail,
                    email = comingExpense.email,
                    groupName = comingExpense.groupName
                )

                val newExpenses = mutableListOf<FbGroupExpense>()
                groupDoc.toObject(FbGroup::class.java)?.groupExpenses?.let {
                    newExpenses.addAll(it)
                }
                newExpenses.add(fbGroupExpense)
                groupDoc.reference.update("groupExpenses", newExpenses).addOnSuccessListener {
                    Timber.i("Group expenses updated for group ${comingExpense.groupName}")
                }.addOnFailureListener { e ->
                    Timber.i("Error updating group expenses for group ${e.localizedMessage}")
                }
            }
        }.addOnFailureListener { e ->
            Timber.i("Error querying for group document ${e.localizedMessage}")
        }
    }

    suspend fun removeGroupExpenseRemoteDb(comingId: String, comingGroup: String) {
        val query = db.collection("groups")
            .whereEqualTo("name", comingGroup)

        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val groupDoc = querySnapshot.documents[0]
                val newExpenses = mutableListOf<FbGroupExpense>()
                groupDoc.toObject(FbGroup::class.java)?.groupExpenses?.let {
                    newExpenses.addAll(it)
                }
                val indexToRemove = newExpenses.indexOfFirst { it.id == comingId }
                if (indexToRemove != -1) {
                    newExpenses.removeAt(indexToRemove)
                    groupDoc.reference.update("groupExpenses", newExpenses).addOnSuccessListener {
                        Timber.i("Group expense with id $comingId removed from group $comingGroup")
                    }.addOnFailureListener { e ->
                        Timber.i("Error removing group expense from group ${e.localizedMessage}")
                    }
                } else {
                    Timber.i("Group expense with id $comingId not found in group $comingGroup")
                }
            }
        }.addOnFailureListener { e ->
            Timber.i("Error querying for group document ${e.localizedMessage}")
        }
    }


    suspend fun loadGroup(): Deferred<List<Group>> = GlobalScope.async {
        val deferred = CompletableDeferred<List<Group>>()
        val temp = mutableListOf<Group>()
        db.collection("groups").get().addOnSuccessListener { result ->
            for (document in result.documents) {
                val doc = document.toObject(FbGroup::class.java)
                Timber.i("Group ${doc?.name}")
                doc?.let {
                    val groupExpenses: RealmList<GroupExpense> = realmListOf()
                    doc.groupExpenses.forEach {
                        val newObj = GroupExpense(
                            id = it.id,
                            description = it.description,
                            amount = it.amount,
                            date = LocalDate.now(),
                            payerEmail = it.payerEmail,
                            email = it.email,
                            groupName = it.groupName
                        )
                        groupExpenses.add(newObj)
                    }
                    temp.add(
                        Group(
                            doc.name,
                            groupExpenses
                        )
                    )
                }
            }
            deferred.complete(temp)
        }.addOnFailureListener { exception ->
            Timber.i("Error getting documents from cloud ")
            deferred.completeExceptionally(exception)
        }
        deferred.await()
    }


    suspend fun loadGroupMembers(): Deferred<List<GroupMembership>> = GlobalScope.async {
        val deferred = CompletableDeferred<List<GroupMembership>>()
        val temp = mutableListOf<GroupMembership>()
        db.collection("groupMemberships").get().addOnSuccessListener { result ->
            for (document in result.documents) {
                val doc = document.toObject(FbGroupMembership::class.java)
                Timber.i("Group Name ${doc?.groupName}")
                doc?.let {
                    temp.add(
                        GroupMembership(
                            doc.groupName,
                            doc.userEmail
                        )
                    )
                }
            }
            deferred.complete(temp)
        }.addOnFailureListener { exception ->
            Timber.i("Error getting documents from cloud ")
            deferred.completeExceptionally(exception)
        }
        deferred.await()
    }


}

