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

class PoetViewModel: ViewModel() {
    val poet = MutableLiveData<Poet>()
    val poetsPoems = MutableLiveData<ArrayList<PoemFinal>>()
    private val poemList = ArrayList<Poem>()
    val poems = ArrayList<PoemFinal>()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()


    private val poetList = ArrayList<Poet>()

    private val poemAPIService = PoemAPIService()
    private val poetAPIService = PoetAPIService()
    private val disposable by lazy { CompositeDisposable() }

    fun getPoetsPoemfromAPI(poet_id : String){

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
        poems.clear()

        disposable.add(
            poemAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({items->
                    val _poet = getPoet(poet_id)
                    for(item in items){
                        if (item.poet_id == _poet.poet_id){
                            poemList.add(item)
                        }
                    }

                    for (p in poemList){
                        val _final_poem = PoemFinal(p.poem_id!!.toInt(),p.poem_title!!,p.poem_text!!,_poet)

                        poems.add(_final_poem)
                    }

                    poet.value = getPoet(poet_id)
                    poetsPoems.value = poems
                    progressBar.value = false
                },
                    { throwable -> throwable.printStackTrace()
                        errorMessage.value = true})
        )


    }

    fun getPoet(id:String):Poet{
        var _poet:Poet? = null
        if (poetList.size!=0){
            for (p in poetList){
                if (p.poet_id == id){
                    _poet = p
                }
            }
        }else{
            _poet = Poet("null","-1","")
        }

        return _poet!!
    }



    fun refreshData(poet_id: String){
        getPoetsPoemfromAPI(poet_id)
    }

}