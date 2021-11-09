package com.example.yummypizza.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.yummypizza.R
import com.example.yummypizza.databinding.PreviewFragmentItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class PreviewPagerAdapter (val context: Context, val urls : List<String>) : PagerAdapter() {
    private var _binding : PreviewFragmentItemBinding? = null
    private val binding get() = _binding!!

    override fun getCount() = urls.size

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        _binding = PreviewFragmentItemBinding.inflate(inflater, container, false)
        Picasso.get().load(urls[position]).into(binding.imageView, object : Callback{
            override fun onSuccess() {
                binding.progressBar.isVisible = false
            }

            override fun onError(e: Exception?) {
            }

        })
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val cont = container as ViewPager
        val view = `object` as View
        cont.removeView(view)

    }
}