package com.example.yummypizza.ui.screens.preview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.yummypizza.MainActivity
import com.example.yummypizza.R
import com.example.yummypizza.appComponent
import com.example.yummypizza.data.database.PizzaDatabaseRepository
import com.example.yummypizza.data.entities.PizzaEntity
import com.example.yummypizza.data.entities.PizzaPicture
import com.example.yummypizza.databinding.PreviewFragmentBinding
import com.example.yummypizza.ui.adapters.PreviewPagerAdapter
import com.example.yummypizza.ui.screens.menu.item.MenuItemBottomSheet
import com.example.yummypizza.ui.screens.menu.item.MenuItemBottomSheetViewModel
import com.example.yummypizza.utils.injections.viewmodels.ViewModelExtensions.injectViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class PreviewFragment : Fragment() {

    private var _binding: PreviewFragmentBinding? = null
    private val binding get() = _binding!!

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PreviewViewModel

    companion object {
        fun newInstance(pizzaIndex: Int) = PreviewFragment().apply {
            arguments = Bundle().apply {
                putInt(MenuItemBottomSheet.TAG, pizzaIndex)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PreviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = requireActivity().appComponent.viewModelFactory()
        viewModel = injectViewModel(viewModelFactory)
        viewModel.bundle = requireArguments()

        val activity = requireActivity() as MainActivity
        activity.changeWindowBars(false)

        loadPizza()
    }

    override fun onDestroy() {
        val activity = requireActivity() as MainActivity
        activity.changeWindowBars(true)
        super.onDestroy()
    }

    private fun loadPizza() {
        viewModel.getSinglePizza()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<PizzaEntity> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: PizzaEntity) {
                    setToolbar(t)
                    loadPictures(t.id)
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    private fun loadPictures(id: Int) {
        PizzaDatabaseRepository.getPicsForPizza(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<PizzaPicture>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<PizzaPicture>) {
                    val list: MutableList<String> = mutableListOf()
                    t.forEach { list.add(it.url) }
                    setPagerAdapter(list)
                    setScrollState(binding.previewPager.currentItem + 1, t.size)
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    private fun setToolbar(pizza: PizzaEntity) {
        binding.previewToolbar.title = pizza.name
        binding.previewToolbar.setNavigationIcon(R.drawable.ic_back)
        binding.previewToolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun setScrollState(position: Int, max: Int) {
        binding.previewScrollState.text = "${position} / ${max}"
    }

    private fun setPagerAdapter(urls: List<String>) {
        val adapter = PreviewPagerAdapter(requireContext(), urls)
        binding.previewPager.adapter = adapter
        binding.previewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                setScrollState(position + 1, adapter.count)
            }

            override fun onPageScrollStateChanged(state: Int) {
                //binding.previewScrollState.text = "$state / ${viewModel.pizzaEntity.imageUrls.size}"
            }
        })

    }
}