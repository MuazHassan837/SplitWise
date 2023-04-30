package com.muazhassan.splitwise.Model

import io.realm.kotlin.types.RealmObject

class User() : RealmObject {
    var email: String = ""

    constructor(email: String)
            : this() {
        this.email = email
    }
}