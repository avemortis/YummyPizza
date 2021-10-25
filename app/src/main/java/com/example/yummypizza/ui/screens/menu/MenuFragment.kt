package com.example.yummypizza.ui.screens.menu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.databinding.MenuFragmentBinding
import com.example.yummypizza.ui.adapters.MenuAdapter
import com.example.yummypizza.ui.adapters.OnMenuItemCLickListener
import com.example.yummypizza.ui.screens.menu.item.MenuItemBottomSheet
import com.example.yummypizza.utils.diffutils.MenuDiffUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MenuFragment : Fragment(), OnMenuItemCLickListener {

    private var _binding: MenuFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MenuViewModel

    private val adapter = MenuAdapter(emptyList(), this)

    companion object {
        fun newInstance() = MenuFragment()
        const val TAG = "MenuFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MenuFragmentBinding.inflate(inflater, container, false)

        prepareRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.getMenu(PizzaService())
        }

        subscribeOnMenu()
        setSearchView()
    }

    override fun onDestroy() {
        viewModel.compositeDisposable.clear()
        super.onDestroy()
    }

    private fun prepareRecyclerView(){
        val recyclerView = binding.menuRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun refreshRecyclerView(list : List<PizzaEntity>){
        val diffUtil = MenuDiffUtil(adapter.menu, list)
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)
        adapter.menu = list
        diffResult.dispatchUpdatesTo(adapter)
    }

    private fun subscribeOnMenu(){
        viewModel.menuObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toObservable()
            .subscribe(object : Observer<List<PizzaEntity>>{
                override fun onSubscribe(d: Disposable) {
                    viewModel.compositeDisposable.add(d)
                }

                override fun onNext(t: List<PizzaEntity>) {
                    refreshRecyclerView(t)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                }
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

    override fun onClick(position: Int) {
        val actualMenu = viewModel.menuObservable.value
        val itemLookerBottomSheet = MenuItemBottomSheet.newInstance(actualMenu[position].id)
        itemLookerBottomSheet.show(childFragmentManager, TAG)
    }

}