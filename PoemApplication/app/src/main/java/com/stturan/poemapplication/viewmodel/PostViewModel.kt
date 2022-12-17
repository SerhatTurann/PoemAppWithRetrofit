package com.stturan.poemapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stturan.poemapplication.model.Poem
import com.stturan.poemapplication.model.PoemFinal
import com.stturan.poemapplication.model.Poet
import com.stturan.poemapplication.service.PoemAPIService
import com.stturan.poemapplication.service.PoetAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostViewModel:ViewModel() {
    val poems = MutableLiveData<ArrayList<PoemFinal>>()
    private val poemList = ArrayList<Poem>()
    val final_poemList = ArrayList<PoemFinal>()
    private val poetList = ArrayList<Poet>()
    private var poet: Poet? = null
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    private val poemAPIService = PoemAPIService()
    private val poetAPIService = PoetAPIService()
    private val disposable by lazy { CompositeDisposable() }




    private fun getDataFromAPI() {

        progressBar.value = true


        poetList.clear()
        disposable.add(
            poetAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({p->
                    poetList.addAll(p)
                    progressBar.value = false
                },{t-> t.printStackTrace()})

        )

        poemList.clear()
        final_poemList.clear()
        disposable.add(
            poemAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({p->
                    poemList.addAll(p)
                    for (p in poemList){
                        val _poet = getPoet(p.poet_id!!.toInt())
                        val _final_poem = PoemFinal(p.poem_id!!.toInt(),p.poem_title!!,p.poem_text!!,_poet)

                        final_poemList.add(_final_poem)
                    }

                    final_poemList.shuffle()
                    poems.value = final_poemList
                },
                    { throwable -> throwable.printStackTrace()})
        )



    }


    fun getPoet(id:Int):Poet{
        if (poetList.size!=0){
            for (p in poetList){
                if (p.poet_id == id.toString()){
                    poet = p
                }
            }
        }else{
            poet = Poet("null","-1","")
        }

        return poet!!
    }


    fun refreshData(){
        getDataFromAPI()
    }



}