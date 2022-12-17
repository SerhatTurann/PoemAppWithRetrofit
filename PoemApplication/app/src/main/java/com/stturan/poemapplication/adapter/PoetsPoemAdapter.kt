package com.stturan.poemapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.stturan.poemapplication.R
import com.stturan.poemapplication.model.PoemFinal
import com.stturan.poemapplication.tool.CreatePlaceHolder
import com.stturan.poemapplication.tool.downloadImage
import com.stturan.poemapplication.view.PoetFragmentDirections
import com.stturan.poemapplication.view.PostFragmentDirections
import kotlinx.android.synthetic.main.recycler_row_poem.view.*
import kotlinx.android.synthetic.main.recycler_row_poets_poem.view.*

class PoetsPoemAdapter (val poemList: ArrayList<PoemFinal>): RecyclerView.Adapter<PoetsPoemAdapter.PoemViewHolder>() {
    class PoemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row_poets_poem,parent,false)
        return PoemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoemViewHolder, position: Int) {
        holder.itemView.recycler_row_poets_poem_poem_title.text = poemList[position].poem_title
        holder.itemView.recycler_row_poets_poem_imageView.downloadImage(poemList[position].poet.img_url,
            CreatePlaceHolder(holder.itemView.context))

        holder.itemView.setOnClickListener {
            val action = PoetFragmentDirections.actionPoetFragmentToPoemFragment(poemList[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return poemList.size
    }

    fun refreshPoemList(newPoemList:ArrayList<PoemFinal>){
        poemList.clear()
        poemList.addAll(newPoemList)
        notifyDataSetChanged()
    }

}