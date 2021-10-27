package com.example.yummypizza.ui.screens.preview

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.yummypizza.MainActivity
import com.example.yummypizza.R
import com.example.yummypizza.databinding.PreviewFragmentBinding
import com.example.yummypizza.ui.adapters.PreviewPagerAdapter

class PreviewFragment : Fragment() {

    private var _binding: PreviewFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = PreviewFragment()
    }

    private lateinit var viewModel: PreviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PreviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PreviewViewModel::class.java)

        val activity = requireActivity() as MainActivity
        activity.changeWindowBars(false)

        setPagerAdapter()
        setToolbar()
        setScrollState(1)
    }

    override fun onDestroy() {
        val activity = requireActivity() as MainActivity
        activity.changeWindowBars(true)
        super.onDestroy()
    }

    private fun setToolbar(){
        binding.previewToolbar.title = viewModel.pizzaEntity.name
        binding.previewToolbar.setNavigationIcon(R.drawable.ic_back)
        binding.previewToolbar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun setScrollState(position : Int){
        binding.previewScrollState.text = "${position} / ${viewModel.pizzaEntity.imageUrls.size}"
    }

    private fun setPagerAdapter(){
        val adapter = PreviewPagerAdapter(requireContext(), viewModel.pizzaEntity.imageUrls)
        binding.previewPager.adapter = adapter
        binding.previewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                setScrollState(position + 1)
            }

            override fun onPageScrollStateChanged(state: Int) {
                //binding.previewScrollState.text = "$state / ${viewModel.pizzaEntity.imageUrls.size}"
            }
        })

    }
}