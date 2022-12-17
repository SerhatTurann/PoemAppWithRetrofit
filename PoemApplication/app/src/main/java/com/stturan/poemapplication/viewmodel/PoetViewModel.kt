package com.stturan.poemapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stturan.poemapplication.model.Poem
import com.stturan.poemapplication.model.PoemFinal
import com.stturan.poemapplication.model.Poet
import com.stturan.poemapplication.service.PoemAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PoetViewModel: ViewModel() {
    val poet = MutableLiveData<Poet>()
    val poetsPoems = MutableLiveData<ArrayList<PoemFinal>>()
    private val poemList = ArrayList<Poem>()
    val poems = ArrayList<PoemFinal>()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    private val poemAPIService = PoemAPIService()
    private val disposable by lazy { CompositeDisposable() }

    fun getPoetfromAPI(_poet: Poet){

        progressBar.value = true
        poemList.clear()
        poet.value = _poet
        disposable.add(
            poemAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({items->
                    for(item in items){
                        if (item.poet_id == poet.value!!.poet_id){
                            poemList.add(item)
                        }
                    }

                    for (p in poemList){
                        val _final_poem = PoemFinal(p.poem_id!!.toInt(),p.poem_title!!,p.poem_text!!,poet.value!!)

                        poems.add(_final_poem)
                    }

                    poetsPoems.value = poems
                    progressBar.value = false
                },
                    { throwable -> throwable.printStackTrace()
                        errorMessage.value = true})
        )


    }



    fun refreshData(poet: Poet){
        getPoetfromAPI(poet)
    }

}