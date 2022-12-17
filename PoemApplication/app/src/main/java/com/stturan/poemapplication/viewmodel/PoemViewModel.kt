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

class PoemViewModel : ViewModel() {
    val poem = MutableLiveData<PoemFinal>()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    private val poemList = ArrayList<Poem>()
    private val poetList = ArrayList<Poet>()
    private var poet: Poet? = null

    private val poemAPIService = PoemAPIService()
    private val poetAPIService = PoetAPIService()
    private val disposable by lazy { CompositeDisposable() }




    private fun getDataFromAPI(poem_id:String) {

        progressBar.value = true


        poetList.clear()
        disposable.add(
            poetAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({p->
                    poetList.addAll(p)
                },{t-> t.printStackTrace()})

        )

        poemList.clear()
        disposable.add(
            poemAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({p->
                    poemList.addAll(p)
                    for (p in poemList){
                        if (p.poem_id == poem_id){
                            val _poet = getPoet(p.poet_id!!.toInt())
                            val _final_poem = PoemFinal(p.poem_id!!.toInt(),p.poem_title!!,p.poem_text!!,_poet)
                            poem.value = _final_poem
                        }
                    }
                    progressBar.value = false

                },
                    { throwable -> throwable.printStackTrace()
                        errorMessage.value = true})
        )



    }


    fun getPoet(id:Int): Poet {
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


    fun refreshData(poem_id: String){
        getDataFromAPI(poem_id)
    }
}