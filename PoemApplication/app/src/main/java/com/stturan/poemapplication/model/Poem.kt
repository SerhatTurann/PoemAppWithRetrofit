package com.stturan.poemapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Poem(
    @ColumnInfo(name = "poem_title")
    @SerializedName("poem_title")
    val poem_title:String,

    @ColumnInfo(name = "poem_text")
    @SerializedName("poem_text")
    val poem_text:String,

    @PrimaryKey
    @ColumnInfo(name = "poem_id")
    @SerializedName("poem_id")
    val poem_id:String,

    @ColumnInfo(name = "poet_id")
    @SerializedName("poet_id")
    val poet_id:String) {
}