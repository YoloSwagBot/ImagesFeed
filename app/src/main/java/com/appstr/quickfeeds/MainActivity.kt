package com.appstr.quickfeeds

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.appstr.quickfeeds.databinding.ActivityMainBinding
import com.appstr.quickfeeds.model.ImageData
import com.appstr.quickfeeds.network.QFNetwork
import com.appstr.quickfeeds.util.exists
import com.appstr.quickfeeds.viewmodel.QuickFeedVM
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: QuickFeedVM
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(QuickFeedVM::class.java)

        viewModel = binding.viewModel as QuickFeedVM

        setupViews()

        // to accurately handle orientation changes with images, implement room and have the adapter
        // read from LiveData and 'recalculate' how to draw the images. This is proper way.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun setupViews(){
        binding.activitySwipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        QFNetwork.requestImageFeed(object : Callback<List<ImageData>>{
            override fun onResponse(call: Call<List<ImageData>>, response: Response<List<ImageData>>) {
                response.body().takeIf { it.exists }?.apply {
//                    forEach {
//                        Log.d("uwotm8", "MainActivity    -    $it")
//                    }
                    viewModel.adapter.setDataset(this)
                    CoroutineScope(Dispatchers.Main).launch {
                        binding.activitySwipeRefreshLayout.isRefreshing = false
                    }
                }
            }
            override fun onFailure(call: Call<List<ImageData>>, t: Throwable) {
                QFNetwork.logFailure(call, t)
                CoroutineScope(Dispatchers.Main).launch {
                    binding.activitySwipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Snackbar.make(binding.root, "The image server gives images slowly. Pull to refresh feed.", Snackbar.LENGTH_INDEFINITE).apply {
            setAction("OK") { this.dismiss() }
            this.show()
        }
    }


}