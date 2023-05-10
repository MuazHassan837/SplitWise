package com.muazhassan.splitwise.Core.Profile.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.muazhassan.splitwise.Model.*
import com.muazhassan.splitwise.R
import com.muazhassan.splitwise.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {

    private val homeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter

    private var email: String? = null
    private var currGroup: Group? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ExpenseAdapter()
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = adapter
        val root: View = binding.root

        email = FirebaseAuth.getInstance().currentUser?.email

        email?.let { nonNullEmail ->
            homeViewModel.setEmail(nonNullEmail)
            homeViewModel.reloadContent(nonNullEmail)
            lifecycleScope.launch {
                homeViewModel.saveUser(nonNullEmail)
                homeViewModel.loadFromCloud(nonNullEmail)
            }
        }

        homeViewModel.lendAmount.observe(viewLifecycleOwner) {
            binding.youAreOwedLabel.text = "You have lend $$it"
        }

        homeViewModel.lendExpenses.observe(viewLifecycleOwner) {
            adapter.setExpenses(it)
        }

        binding.fab.setOnClickListener {
            currGroup?.let {
                homeViewModel.launchAddExpenseActivity(this)
            } ?: Toast.makeText(requireContext(), "Select Group First please", Toast.LENGTH_SHORT).show()
        }

        binding.changeGroupButton.setOnClickListener {
            showPopupMenu(binding.changeGroupButton)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    val swipeHandler =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // Ignore move gestures
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                println("id is ${adapter.expenses[position].id}")
                lifecycleScope.launch {
                    homeViewModel.removeExpenseWithId(
                        adapter.expenses[position].id,
                        adapter.expenses[position].groupName
                    )
                }
                adapter.removeItem(position)
            }

        }


    fun menuGroupAction(GroupName: String) {
        binding.groupTextview.text = "Selected group: $GroupName"
        lifecycleScope.launch {
            homeViewModel.saveGroup(GroupName)
        }
        currGroup = Group(GroupName)
    }

    fun menuMemberCheck(GroupName: String) {
        lifecycleScope.launch {
            Timber.i("here in menu $email!!")
            homeViewModel.saveGroupRelation(GroupName, email!!)
        }
    }

    fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(R.menu.group_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_group_1 -> {
                    var groupId = "Group 1"
                    menuGroupAction(groupId)
                    menuMemberCheck(groupId)
                    true
                }
                R.id.menu_group_2 -> {
                    var groupId = "Group 2"
                    menuGroupAction(groupId)
                    menuMemberCheck(groupId)
                    true
                }
                R.id.menu_group_3 -> {
                    var groupId = "Group 3"
                    menuGroupAction(groupId)
                    menuMemberCheck(groupId)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 837 && resultCode == Activity.RESULT_OK && data != null) {
            val amount = data.getDoubleExtra("amount", 0.0)
            val splitStrategy = data.getSerializableExtra("splitStrategy") as? SplitStrategy
            val desp = data.getStringExtra("description")
            val splitVal = data.getIntExtra("sliderValue", 0)

            if (amount != null && splitStrategy != null) {
                lifecycleScope.launch {
                    homeViewModel.saveLendExpenseWith(
                        amount,
                        email!!,
                        splitStrategy,
                        desp!!,
                        currGroup!!.name,
                        splitVal
                    )
                }
            }
        }
    }


}