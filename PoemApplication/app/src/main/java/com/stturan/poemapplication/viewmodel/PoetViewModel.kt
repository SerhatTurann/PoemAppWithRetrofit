package com.stturan.poemapplication.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.stturan.poemapplication.model.PoemFinal
import com.stturan.poemapplication.model.Poet
import com.stturan.poemapplication.service.PoemDatabase
import kotlinx.coroutines.launch

class PoetViewModel(application: Application) : BaseViewModel(application){
    val poet = MutableLiveData<Poet>()
    val poetsPoems = MutableLiveData<ArrayList<PoemFinal>>()
    val poemList = ArrayList<PoemFinal>()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()


    fun getData(poet_id: String){
        progressBar.value=true
        poemList.clear()
        launch {
            poet.value = PoemDatabase(getApplication()).poetDao().getPoet(poet_id)
            val _poemList = PoemDatabase(getApplication()).poemDao().getPoetsPoem(poet_id)

            for (item in _poemList){
                poemList.add(PoemFinal(item.poem_id.toInt(),item.poem_title,item.poem_text, poet.value!!))
            }

            showItems(poemList)
            Toast.makeText(getApplication(),"Roomdan aldık.", Toast.LENGTH_LONG).show()
        }
    }

    private fun showItems(item : ArrayList<PoemFinal>){
        poetsPoems.value = item
        errorMessage.value = false
        progressBar.value = false
    }

    fun refreshData(poet_id: String){
        getData(poet_id)
    }

}