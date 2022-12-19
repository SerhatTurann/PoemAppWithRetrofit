package com.stturan.poemapplication.service

import com.stturan.poemapplication.model.Poet
import io.reactivex.Single
import retrofit2.http.GET

interface PoetAPI {
    @GET("SerhatTurann/PoemAppWithRetrofit/main/poet.json")
    fun getPoet() : Single<ArrayList<Poet>>


}