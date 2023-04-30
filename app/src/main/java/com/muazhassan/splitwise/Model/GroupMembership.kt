package com.muazhassan.splitwise.Model

import io.realm.kotlin.types.RealmObject

class GroupMembership() : RealmObject {
    var groupName: String = ""
    var userEmail: String = ""

    constructor(groupName: String, userEmail: String)
            : this() {
        this.groupName = groupName
        this.userEmail = userEmail
    }
}

class FbGroupMembership {
    var groupName: String = ""
    var userEmail: String = ""

    constructor()

    constructor(groupName: String, userEmail: String) {
        this.groupName = groupName
        this.userEmail = userEmail
    }
}
