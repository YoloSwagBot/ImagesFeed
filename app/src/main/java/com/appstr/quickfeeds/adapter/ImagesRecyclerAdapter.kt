package com.appstr.quickfeeds.adapter

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.appstr.quickfeeds.R
import com.appstr.quickfeeds.databinding.VhImagesfeedBinding
import com.appstr.quickfeeds.model.ImageData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class ImagesRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var widthPixels: Int = Resources.getSystem().displayMetrics.widthPixels
    private var heightPixels: Int = Resources.getSystem().displayMetrics.heightPixels

    var dataset = arrayListOf<ImageData>()

    override fun onCreateViewHolder(p: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(p.context),
                R.layout.vh_imagesfeed,
                p,
                false
            )
        )
    }

    fun setDataset(datas: List<ImageData>){
        dataset.clear()
        dataset.addAll(datas)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, p: Int) {
        when (h){
            is ImageVH -> h.bind(dataset[p], widthPixels, heightPixels)
        }
    }

    class ImageVH(val binding: VhImagesfeedBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(imageData: ImageData, screenWidth: Int, screenHeight: Int){
            // calc image dimensions for a nice user experience
            val imgWidth = imageData.width
            val imgHeight = imageData.height
            var glideWidth = 0
            var glideHeight = 0
            binding.vhImageview.let { imgView ->
                imgView.layoutParams.apply {
                    width = screenWidth
                    height = (imgHeight * screenWidth) / imgWidth
                    glideWidth = width
                    glideHeight = height
                }
            }

            // Image load
            binding.vh0ContentProgress.visibility = View.VISIBLE
            Glide.with(binding.root.context)
                .load(imageData.imgUrl)
                .override(glideWidth, glideHeight)
                .addListener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.vh0ContentProgress.visibility = View.GONE
                        return false
                    }
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
//                        Log.d("uwotm8", "ImagesRecyclerAdapter    -    onLoadFailed(...)     -    exception: $e")
                        binding.vh0ContentProgress.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.vhImageview)
        }
    }

}