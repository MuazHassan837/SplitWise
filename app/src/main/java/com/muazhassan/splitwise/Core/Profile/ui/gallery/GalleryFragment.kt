package com.muazhassan.splitwise.Core.Profile.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.muazhassan.splitwise.Core.Profile.ui.home.ExpenseAdapter
import com.muazhassan.splitwise.Core.Profile.ui.home.HomeViewModel
import com.muazhassan.splitwise.databinding.FragmentGalleryBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OwnedAdapter

    private val galleryViewModel by lazy {
        ViewModelProvider(this).get(GalleryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerView1
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = OwnedAdapter()
        recyclerView.adapter = adapter

        val root: View = binding.root
        val email = FirebaseAuth.getInstance().currentUser?.email

        email?.let { nonNullEmail ->
            galleryViewModel.reloadContent(nonNullEmail)
        }
        galleryViewModel.ownedAmount.observe(viewLifecycleOwner) {
            binding.youAreOwedLabel1.text = "You are owned $$it"
        }
        galleryViewModel.ownedExpenses.observe(viewLifecycleOwner) {
            adapter.setExpenses(it)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}