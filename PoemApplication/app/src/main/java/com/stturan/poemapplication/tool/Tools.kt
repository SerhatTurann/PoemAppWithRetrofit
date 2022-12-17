package com.stturan.poemapplication.tool

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.stturan.poemapplication.R


//https://raw.githubusercontent.com/SerhatTurann/PoemAppWithRetrofit/main/poets_images/ahmet_arif.png

fun ImageView.downloadImage(url : String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun CreatePlaceHolder(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f //yükleniyor işaretinin kalınlığı float
        centerRadius = 40f // yükleniyor işaretinin çapı
        start()
    }

}



/*
@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView, url:String){
    view.downloadImage(url, CreatePlaceHolder(view.context))
}*/