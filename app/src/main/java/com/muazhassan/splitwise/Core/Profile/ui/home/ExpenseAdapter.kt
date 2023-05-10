package com.muazhassan.splitwise.Core.Profile.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muazhassan.splitwise.Model.GroupExpense
import com.muazhassan.splitwise.R
import java.math.BigDecimal
import java.time.LocalDate


open class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {

    val expenses = mutableListOf<GroupExpense>()


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expenseId: TextView = itemView.findViewById(R.id.expense_id)
        val expenseAmount: TextView = itemView.findViewById(R.id.expense_amount)
        val expenseEmail: TextView = itemView.findViewById(R.id.expense_email)
        var expenseDescription : TextView = itemView.findViewById(R.id.expense_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = expenses?.get(position)
        expense?.let {
            holder.expenseId.text = it.groupName
            holder.expenseAmount.text = it.amount.toString()
            holder.expenseEmail.text = it.email
            holder.expenseDescription.text = it.description
        }
    }

    override fun getItemCount(): Int {
        return expenses?.size ?: 0
    }


    fun setExpenses(expenses: List<GroupExpense>) {
        this.expenses?.clear()
        this.expenses?.addAll(expenses)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        expenses.removeAt(position)
        notifyItemRemoved(position)
    }
}
