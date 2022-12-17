package com.stturan.poemapplication.service

import com.stturan.poemapplication.model.Poem
import io.reactivex.Single
import retrofit2.http.GET

interface PoemAPI {

    //Get, Post

    //https://github.com/atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json

    //Base_url -> https://github.com/

    //atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json

    @GET("SerhatTurann/PoemAppWithRetrofit/main/poem.json")
    fun getPoem() : Single<ArrayList<Poem>>


}