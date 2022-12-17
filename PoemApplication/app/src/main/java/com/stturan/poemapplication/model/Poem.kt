package com.stturan.poemapplication.model

import com.google.gson.annotations.SerializedName

data class Poem(
    @SerializedName("poem_title")
    val poem_title:String?,
    @SerializedName("poem_text")
    val poem_text:String?,
    @SerializedName("poem_id")
    val poem_id:String?,
    @SerializedName("poet_id")
    val poet_id:String?) {
}