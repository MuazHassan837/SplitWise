package com.muazhassan.splitwise.Core.Profile.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.muazhassan.splitwise.Model.GroupExpense
import com.muazhassan.splitwise.R



open class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {



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

    }

    override fun getItemCount(): Int {
    }


    fun setExpenses(expenses: List<GroupExpense>) {

        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        notifyItemRemoved(position)
    }
}
