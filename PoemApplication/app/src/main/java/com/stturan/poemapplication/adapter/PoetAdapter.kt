package com.stturan.poemapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stturan.poemapplication.R
import com.stturan.poemapplication.model.Poet
import kotlinx.android.synthetic.main.recycler_row_poet.view.*

class PoetAdapter(val poetList: ArrayList<Poet>): RecyclerView.Adapter<PoetAdapter.PoetViewHolder>() {
    class PoetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row_poet,parent,false)
        return PoetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoetViewHolder, position: Int) {
        holder.itemView.recycler_row_poet_poet_name.text = poetList[position].poet_name
    }

    override fun getItemCount(): Int {
        return poetList.size
    }

    fun refreshPoetList(newPoetList:ArrayList<Poet>){
        poetList.clear()
        poetList.addAll(newPoetList)
        notifyDataSetChanged()
    }

}