package com.example.yummypizza.ui.screens.menu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummypizza.databinding.MenuFragmentBinding
import com.example.yummypizza.ui.adapters.MenuAdapter
import com.example.yummypizza.ui.adapters.OnMenuItemCLickListener
import com.example.yummypizza.ui.screens.menu.item.MenuItemBottomSheet

class MenuFragment : Fragment(), OnMenuItemCLickListener {

    private var _binding: MenuFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MenuFragment()
        const val TAG = "MenuFragment"
    }

    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MenuFragmentBinding.inflate(inflater, container, false)

        setOnClickListeners()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        startLiveDataWatch()
        setSearchView()


    }

    private fun setOnClickListeners(){
    }

    private fun startLiveDataWatch(){
        val recyclerView = binding.menuRecyclerView
        val adapter = MenuAdapter(mutableListOf(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.menuLiveData.observe(viewLifecycleOwner, {
            adapter.menu = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun setSearchView(){
        binding.menuSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchByMenu(newText)
                return false
            }

        })
    }

    private fun showMenuItemLooker(){
    }

    override fun onClick(position: Int) {
        val actualMenu = viewModel.menuLiveData.value!!
        val itemLookerBottomSheet = MenuItemBottomSheet.newInstance(actualMenu[position].id - 1)
        itemLookerBottomSheet.show(childFragmentManager, TAG)
    }

}