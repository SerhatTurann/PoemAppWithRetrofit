package com.stturan.poemapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Poet(
    @ColumnInfo(name = "poet_name")
    @SerializedName("poet_name")
    val poet_name:String,
    @PrimaryKey
    @ColumnInfo(name = "poet_id")
    @SerializedName("poet_id")
    val poet_id:String,
    @ColumnInfo(name = "img_url")
    @SerializedName("img_url")
    val img_url:String) {
}