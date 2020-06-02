package com.appstr.quickfeeds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.appstr.quickfeeds.adapter.ImagesRecyclerAdapter

class QuickFeedVM(appli: Application) : AndroidViewModel(appli){

    var adapter = ImagesRecyclerAdapter()

}