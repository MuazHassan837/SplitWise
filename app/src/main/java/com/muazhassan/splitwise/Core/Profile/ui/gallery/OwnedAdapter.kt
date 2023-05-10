package com.muazhassan.splitwise.Core.Profile.ui.gallery

import com.muazhassan.splitwise.Core.Profile.ui.home.ExpenseAdapter

class OwnedAdapter : ExpenseAdapter() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = expenses?.get(position)
        expense?.let {
            holder.expenseId.text = it.groupName
            holder.expenseAmount.text = it.amount.toString()
            holder.expenseEmail.text = it.payerEmail
            holder.expenseDescription.text = it.description
        }
    }
}