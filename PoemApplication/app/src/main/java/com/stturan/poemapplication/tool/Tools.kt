package com.stturan.poemapplication.tool

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.stturan.poemapplication.R



fun ImageView.downloadImage(url : String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun CreatePlaceHolder(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }

}

@BindingAdapter("android:setBackgroundImage")
fun setBackgroundImage(view: ImageView,url:String){
    view.downloadImage(url, CreatePlaceHolder(view.context))
}

@BindingAdapter(value = ["app:setBackgroundImage","app:context"], requireAll = true)
fun setBackgroundImage(view: ImageView,url:String?,context: Context) {
    Glide
        .with(context)
        .setDefaultRequestOptions(RequestOptions().placeholder(CreatePlaceHolder(context)).error(R.mipmap.ic_launcher_round))
        .load(url)
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher_round)
        .into(view)
}

