package com.muazhassan.splitwise.Model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore

class Group() : RealmObject {
    var name: String = ""
    var groupExpenses: RealmList<GroupExpense> = realmListOf()

    constructor(name: String, groupExpenses: RealmList<GroupExpense> = realmListOf())
            : this() {
        this.name = name
        this.groupExpenses = groupExpenses
    }
}

class FbGroup {
    var name: String = ""
    var groupExpenses: MutableList<FbGroupExpense> = mutableListOf()

    constructor()

    constructor(name: String, groupExpenses: MutableList<FbGroupExpense> = mutableListOf()) {
        this.name = name
        this.groupExpenses = groupExpenses
    }
}

