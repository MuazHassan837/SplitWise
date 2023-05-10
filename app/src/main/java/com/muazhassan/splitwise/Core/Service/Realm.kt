package com.muazhassan.splitwise.Core.Service

import android.app.Application
import com.muazhassan.splitwise.Model.Group
import com.muazhassan.splitwise.Model.GroupExpense
import com.muazhassan.splitwise.Model.GroupMembership
import com.muazhassan.splitwise.Model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmManager() {

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

    suspend fun saveUserLocalDb(comingEmail: String) {
        val newUser = User(comingEmail)
        val existingUser =
            realm.query(clazz = User::class, "email == $0", comingEmail).find().size
        if (existingUser == 0) {
            realm.write {
                copyToRealm(newUser)
            }
        }
    }

    suspend fun saveGroupLocalDb(comingGroup: String) {
        val existingGroup =
            realm.query(clazz = Group::class, "name == $0", comingGroup).find().size
        if (existingGroup == 0) {
            val newGroup = Group(comingGroup)
            realm.write {
                copyToRealm(newGroup)
            }
        }
    }

    suspend fun saveGroupMemberRelationLocalDb(comingGroupName: String, comingEmail: String){
        val members =
            realm.query(clazz = GroupMembership::class, "groupName == $0", comingGroupName).find()

        var isPart = members.any { it.userEmail == comingEmail }

        if (!isPart) {
            val newGroupMember = GroupMembership(comingGroupName, comingEmail)
            realm.write {
                copyToRealm(newGroupMember)
            }
        }
    }

    suspend fun removeExpenseLocalDb(comingId: String) {
        realm.write {
            val refToObject = query(clazz = GroupExpense::class, "id == $0", comingId).find().first()
            delete(refToObject)
        }
    }



}
