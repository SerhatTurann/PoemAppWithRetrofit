package com.stturan.poemapplication.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.stturan.poemapplication.model.Poem
import com.stturan.poemapplication.model.PoemFinal
import com.stturan.poemapplication.model.Poet
import com.stturan.poemapplication.service.PoemAPIService
import com.stturan.poemapplication.service.PoemDatabase
import com.stturan.poemapplication.service.PoetAPIService
import com.stturan.poemapplication.tool.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : BaseViewModel(application) {
    val poems = MutableLiveData<ArrayList<PoemFinal>>()
    private val poemList = ArrayList<PoemFinal>()
    private val poetList = ArrayList<Poet>()

    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    private val poemAPIService = PoemAPIService()
    private val poetAPIService = PoetAPIService()
    private val disposable by lazy { CompositeDisposable() }


    private val customSharedPreferences = CustomSharedPreferences(getApplication())
    val updateTimeCoefficientNanoseconds = 60 * 1000 * 1000 * 1000L
    val updateTimeMinute = 0.1
    private var updateTime = updateTimeMinute*updateTimeCoefficientNanoseconds

    fun refreshData(){

        val saveTime = customSharedPreferences.getTime()
        if (saveTime != null && saveTime != 0L && System.nanoTime() - saveTime<updateTime){
            getDataFromRoom()
        }else{
            getDataFromAPI()
        }
    }

    fun refreshSwipeLayout(){
        getDataFromAPI()
    }

    private fun getDataFromAPI() {

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

        disposable.add(
            poemAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({p->
                    saveRoom(p,poetList)
                    Toast.makeText(getApplication(),"API'dan aldık.", Toast.LENGTH_LONG).show()
                },
                    { throwable -> throwable.printStackTrace()})
        )
    }

    private fun getDataFromRoom(){
        progressBar.value = true
        poemList.clear()
        launch {
            val _poem = PoemDatabase(getApplication()).poemDao().getAllPoem()

            for (item in _poem){
                poemList.add(PoemFinal(item.poem_id.toInt(),item.poem_title,item.poem_text,
                    PoemDatabase(getApplication()).poetDao().getPoet(item.poet_id)))
            }

            showItems(poemList)
            Toast.makeText(getApplication(),"Roomdan aldık.", Toast.LENGTH_LONG).show()
        }
    }


    private fun showItems(itemList : ArrayList<PoemFinal>){
        poems.value = itemList
        errorMessage.value = false
        progressBar.value = false
    }

    private fun saveRoom(_poemList: ArrayList<Poem>,_poetList: ArrayList<Poet>){
        poemList.clear()
        launch {
            PoemDatabase(getApplication()).poetDao().deleteAllPoet()
            PoemDatabase(getApplication()).poetDao().insertAll(*_poetList.toTypedArray())
            PoemDatabase(getApplication()).poemDao().deleteAllPoem()
            PoemDatabase(getApplication()).poemDao().insertAll(*_poemList.toTypedArray())

            for (item in _poemList){
                poemList.add(PoemFinal(item.poem_id.toInt(),item.poem_title,item.poem_text,
                    _poetList[item.poet_id.toInt()]))
            }
            poemList.shuffle()
            showItems(poemList)
        }

        customSharedPreferences.saveTime(System.nanoTime())
    }



}