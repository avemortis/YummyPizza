package com.example.yummypizza.ui.screens.menu.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.yummypizza.data.api.PizzaService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.yummypizza.databinding.MenuItemBottomSheetBinding
import com.example.yummypizza.ui.screens.preview.PreviewFragment
import com.example.yummypizza.utils.navigation.FragmentNavigator
import com.example.yummypizza.utils.navigation.FragmentNavigator.show
import com.squareup.picasso.Picasso
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MenuItemBottomSheet : BottomSheetDialogFragment() {
    private var _binding: MenuItemBottomSheetBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MenuItemBottomSheetViewModel

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
        viewModel = ViewModelProvider(this).get(MenuItemBottomSheetViewModel::class.java)
        viewModel.bundle = requireArguments()

        if (savedInstanceState == null) {
            viewModel.getPizza(PizzaService())
            watchLoadStatus()
        } else {
            prepareViews()
        }
    }

    override fun onDestroy() {
        viewModel.compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun setOnClickListeners(){
        binding.menuBottomSheetImage.setOnClickListener {
            try {
                onImageItemClickListener?.onImageClick(viewModel.pizzaEntity.id)
                this.dismiss()
            } catch (e : Exception) {
            }
        }
    }

    fun prepareViews(){
        try {
            binding.menuBottomSheetTitle.text = viewModel.pizzaEntity.name
            binding.menuBottomSheetDescription.text = viewModel.pizzaEntity.description
            binding.menuBottomSheetAddtocart.text = "${binding.menuBottomSheetAddtocart.text} ${viewModel.pizzaEntity.price}"
            binding.itemProgress.isVisible = false
            Picasso.get().load(viewModel.pizzaEntity.imageUrls[0]).into(binding.menuBottomSheetImage)
        } catch (e : Exception) {
            viewModel.getPizza(PizzaService())
            watchLoadStatus()
        }
    }

    private fun watchLoadStatus(){
        viewModel.loadStatus
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Boolean> {
                override fun onSubscribe(d: Disposable) {
                    viewModel.compositeDisposable.add(d)
                }

                override fun onNext(t: Boolean) {
                    prepareViews()
                    setOnClickListeners()
                }
                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

    companion object {
        const val TAG = "MenuLooker"
        fun newInstance(pizzaIndex : Int) = MenuItemBottomSheet().apply{
            arguments = Bundle().apply {
                putInt(TAG, pizzaIndex)
            }
        }
    }

    interface OnImageItemClickListener {
        fun onImageClick(id : Int)
    }

}