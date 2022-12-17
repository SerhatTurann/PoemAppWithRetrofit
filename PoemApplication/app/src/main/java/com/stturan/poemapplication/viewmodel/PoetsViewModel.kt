package com.stturan.poemapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stturan.poemapplication.model.Poet
import com.stturan.poemapplication.service.PoetAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PoetsViewModel: ViewModel() {
    val poets = MutableLiveData<ArrayList<Poet>>()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    private val poetAPIService = PoetAPIService()
    private val disposable = CompositeDisposable()


    fun refreshData(){
        getDataFromAPI()
    }

    private fun getDataFromAPI() {
        progressBar.value = true

        disposable.add(
            poetAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<Poet>>() {
                    override fun onSuccess(t: ArrayList<Poet>) {
                        poets.value= t
                        progressBar.value = false
                    }

                    override fun onError(e: Throwable) {
                        errorMessage.value = true
                        progressBar.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

}