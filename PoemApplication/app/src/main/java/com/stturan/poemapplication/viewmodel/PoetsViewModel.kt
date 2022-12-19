package com.stturan.poemapplication.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.stturan.poemapplication.model.Poet
import com.stturan.poemapplication.service.PoemDatabase
import kotlinx.coroutines.launch

class PoetsViewModel(application: Application) : BaseViewModel(application) {
    val poets = MutableLiveData<List<Poet>>()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    fun refreshData(){
        getData()
    }

    fun getData(){
        progressBar.value = true
        launch {
            poets.value = PoemDatabase(getApplication()).poetDao().getAllPoet()
        }
    }

}