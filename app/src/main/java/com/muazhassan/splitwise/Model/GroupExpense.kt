package com.muazhassan.splitwise.Model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import java.math.BigDecimal
import java.security.Timestamp
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class GroupExpense() : RealmObject {
    var id: String = ""
    var description: String = ""
    var amount: Double = 0.0
    var date: String = ""
    var payerEmail: String = ""
    var email: String = ""
    var groupName: String = ""

    constructor(id: String, description: String, amount: Double, date: LocalDate, payerEmail: String, email: String, groupName: String)
            : this() {
        this.id = if (id.isBlank()) UUID.randomUUID().toString() else id
        this.description = description
        this.amount = amount
        this.date = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        this.payerEmail = payerEmail
        this.email = email
        this.groupName = groupName
    }

    constructor(description: String, amount: Double, date: LocalDate, payerEmail: String, email: String, groupName: String)
            : this(UUID.randomUUID().toString(), description, amount, date, payerEmail, email, groupName)
}


class FbGroupExpense {
    var id: String = ""
    var description: String = ""
    var amount: Double = 0.0
    var date: String = ""
    var payerEmail: String = ""
    var email: String = ""
    var groupName: String = ""

    constructor()

    constructor(id: String, description: String, amount: Double, date: LocalDate, payerEmail: String, email: String, groupName: String) {
        this.id = id
        this.description = description
        this.amount = amount
        this.date = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        this.payerEmail = payerEmail
        this.email = email
        this.groupName = groupName
    }
}
