package com.stturan.poemapplication.service

import com.stturan.poemapplication.model.Poem
import com.stturan.poemapplication.model.Poet
import io.reactivex.Single
import retrofit2.http.GET

interface PoetAPI {

    //Get, Post

    //https://github.com/atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json

    //Base_url -> https://github.com/

    //atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json

    @GET("SerhatTurann/PoemAppWithRetrofit/main/poet.json")
    fun getPoet() : Single<ArrayList<Poet>>


}