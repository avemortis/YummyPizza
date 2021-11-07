package com.example.yummypizza.ui.screens.menu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.example.yummypizza.appComponent
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.databinding.MenuFragmentBinding
import com.example.yummypizza.ui.adapters.MenuAdapter
import com.example.yummypizza.ui.adapters.OnMenuItemCLickListener
import com.example.yummypizza.ui.screens.menu.item.MenuItemBottomSheet
import com.example.yummypizza.ui.screens.preview.PreviewFragment
import com.example.yummypizza.ui.adapters.LinearLayoutManagerWrapper
import com.example.yummypizza.utils.diffutils.MenuDiffUtil
import com.example.yummypizza.utils.injections.viewmodels.ViewModelExtensions.injectViewModel
import com.example.yummypizza.utils.navigation.FragmentNavigator
import com.example.yummypizza.utils.navigation.FragmentNavigator.show
import com.squareup.picasso.Picasso
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MenuFragment : Fragment(), OnMenuItemCLickListener,
    MenuItemBottomSheet.OnImageItemClickListener {

    private var _binding: MenuFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MenuViewModel
    private val compositeDisposable = CompositeDisposable()

    private val adapter = MenuAdapter(0, this)

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
        viewModelFactory = requireActivity().appComponent.viewModelFactory()
        viewModel = injectViewModel(viewModelFactory)

        if (savedInstanceState == null) {
            val service = requireActivity().appComponent.getPizzaService()
            viewModel.getMenu(service)
        }

        subscribeOnMenu()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun prepareRecyclerView() {
        val recyclerView = binding.menuRecyclerView
        recyclerView.layoutManager = LinearLayoutManagerWrapper(requireContext())
        recyclerView.adapter = adapter
    }

    @MainThread
    private fun refreshRecyclerView(list: List<PizzaEntity>) {
        val diffUtil = MenuDiffUtil(viewModel.prevMenu, list)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)
        adapter.size = list.size
        diffResult.dispatchUpdatesTo(adapter)
    }

    private fun subscribeOnMenu() {
        viewModel.menuObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toObservable()
            .subscribe(object : Observer<List<PizzaEntity>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: List<PizzaEntity>) {
                    if (t.isNotEmpty()) {
                        binding.menuProgress.isVisible = false
                        refreshRecyclerView(t)
                    }
                    setSearchView()
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {
                }
            })
    }

    private fun setSearchView() {
        binding.menuSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        itemLookerBottomSheet.onImageItemClickListener = this
        itemLookerBottomSheet.show(childFragmentManager, TAG)
    }

    override fun onCreateViewHolder(holder: MenuAdapter.MenuHolder, position: Int) {
        holder.title.text = viewModel.menuObservable.value[position].name
        holder.info.text = viewModel.menuObservable.value[position].description
        holder.price.text = viewModel.menuObservable.value[position].price.toString()
        Picasso.get().load(viewModel.menuObservable.value[position].imageUrls[0]).into(holder.image)
    }

    override fun onImageClick(id: Int) {
        val fragment = PreviewFragment.newInstance()
        fragment.show(parentFragmentManager, FragmentNavigator.root)
    }
}