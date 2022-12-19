package com.stturan.poemapplication.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.stturan.poemapplication.model.PoemFinal
import com.stturan.poemapplication.service.PoemDatabase
import kotlinx.coroutines.launch

class PoemViewModel(application: Application) : BaseViewModel(application) {
    val poem = MutableLiveData<PoemFinal>()
    val errorMessage = MutableLiveData<Boolean>()
    val progressBar = MutableLiveData<Boolean>()

    fun getData(poem_id: String){
        progressBar.value = true
        launch {
            val _poem = PoemDatabase(getApplication()).poemDao().getPoem(poem_id)
            val _poet = PoemDatabase(getApplication()).poetDao().getPoet(_poem.poet_id!!)

            showItems(PoemFinal(_poem.poem_id!!.toInt(),_poem.poem_title!!,_poem.poem_text!!,_poet))
            Toast.makeText(getApplication(),"Roomdan aldık.", Toast.LENGTH_LONG).show()
        }
    }

    private fun showItems(item : PoemFinal){
        poem.value = item
        errorMessage.value = false
        progressBar.value = false
    }


    fun refreshData(poem_id: String){
        getData(poem_id)
    }
}