package com.stturan.poemapplication.service

import com.stturan.poemapplication.model.Poem
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PoemAPIService {

    //

    //https://github.com/atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json

    //Base_url -> https://github.com/

    //atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json
    //"https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/blob/master/besinler.json"

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().
        create(PoemAPI::class.java)

    fun getData() : Single<ArrayList<Poem>>{
        return api.getPoem()
    }
}