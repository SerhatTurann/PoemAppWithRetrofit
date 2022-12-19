package com.stturan.poemapplication.service

import com.stturan.poemapplication.model.Poem
import io.reactivex.Single
import retrofit2.http.GET

interface PoemAPI {

    @GET("SerhatTurann/PoemAppWithRetrofit/main/poem.json")
    fun getPoem() : Single<ArrayList<Poem>>


}