package com.example.yummypizza.ui.screens.menu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.databinding.MenuFragmentBinding
import com.example.yummypizza.ui.adapters.MenuAdapter
import com.example.yummypizza.ui.adapters.OnMenuItemCLickListener
import com.example.yummypizza.ui.screens.menu.item.MenuItemBottomSheet
import com.example.yummypizza.utils.diffutils.MenuDiffUtil
import io.reactivex.disposables.CompositeDisposable

class MenuFragment : Fragment(), OnMenuItemCLickListener {

    private var _binding: MenuFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MenuViewModel
    private val compositeDisposable = CompositeDisposable()

    companion object {
        fun newInstance() = MenuFragment()
        const val TAG = "MenuFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MenuFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.getMenu(PizzaService())
        }

        startLiveDataWatch()
        setSearchView()
    }

    override fun onDestroy() {
        viewModel.compositeDisposable.clear()
        super.onDestroy()
    }

    private fun setOnClickListeners(){
    }

    private fun startLiveDataWatch(){
        val recyclerView = binding.menuRecyclerView
        val adapter = MenuAdapter(mutableListOf(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.menuLiveData.observe(viewLifecycleOwner, {
            val diffUtil = MenuDiffUtil(adapter.menu, it)
            val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)
            adapter.menu = it
            diffResult.dispatchUpdatesTo(adapter)
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
        val itemLookerBottomSheet = MenuItemBottomSheet.newInstance(actualMenu[position].id)
        itemLookerBottomSheet.show(childFragmentManager, TAG)
    }

}