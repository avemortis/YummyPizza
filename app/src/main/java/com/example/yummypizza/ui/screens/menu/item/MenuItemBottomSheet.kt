package com.example.yummypizza.ui.screens.menu.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.yummypizza.appComponent
import com.example.yummypizza.data.api.PizzaService
import com.example.yummypizza.data.entities.PizzaEntity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.yummypizza.databinding.MenuItemBottomSheetBinding
import com.example.yummypizza.ui.screens.preview.PreviewFragment
import com.example.yummypizza.ui.screens.preview.PreviewViewModel
import com.example.yummypizza.utils.injections.viewmodels.ViewModelExtensions.injectViewModel
import com.example.yummypizza.utils.navigation.FragmentNavigator
import com.example.yummypizza.utils.navigation.FragmentNavigator.show
import com.squareup.picasso.Picasso
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MenuItemBottomSheet : BottomSheetDialogFragment() {
    private var _binding: MenuItemBottomSheetBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MenuItemBottomSheetViewModel

    val compositeDisposable = CompositeDisposable()

    var onImageItemClickListener: OnImageItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MenuItemBottomSheetBinding
            .inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = requireActivity().appComponent.viewModelFactory()
        viewModel = injectViewModel(viewModelFactory)
        if (savedInstanceState == null) {
            viewModel.bundle = requireArguments()
        }

        setOnClickListeners()
        watchLoadStatus()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun setOnClickListeners() {
        binding.menuBottomSheetImage.setOnClickListener {
            try {
                onImageItemClickListener?.onImageClick(viewModel.index)
                this.dismiss()
            } catch (e: Exception) {
            }
        }
    }

    fun prepareViews(pizzaEntity: PizzaEntity) {
        binding.menuBottomSheetTitle.text = pizzaEntity.name
        binding.menuBottomSheetDescription.text = pizzaEntity.description
        binding.menuBottomSheetAddtocart.text =
            "${binding.menuBottomSheetAddtocart.text} ${pizzaEntity.price}"
        binding.itemProgress.isVisible = false
        Picasso.get().load(pizzaEntity.imageUrls[0]).into(binding.menuBottomSheetImage)
    }

    private fun watchLoadStatus() {
        val service = requireActivity().appComponent.getPizzaService()
        viewModel.getSinglePizza(service)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<PizzaEntity> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: PizzaEntity) {
                    prepareViews(t)
                }

                override fun onError(e: Throwable) {
                }

            })
    }

    companion object {
        const val TAG = "MenuLooker"
        fun newInstance(pizzaIndex: Int) = MenuItemBottomSheet().apply {
            arguments = Bundle().apply {
                putInt(TAG, pizzaIndex)
            }
        }
    }

    interface OnImageItemClickListener {
        fun onImageClick(id: Int)
    }

}