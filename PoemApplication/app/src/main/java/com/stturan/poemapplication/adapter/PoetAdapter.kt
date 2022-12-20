package com.stturan.poemapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.stturan.poemapplication.R
import com.stturan.poemapplication.databinding.RecyclerRowPoetBinding
import com.stturan.poemapplication.model.Poet

class PoetAdapter(val poetList: ArrayList<Poet>): RecyclerView.Adapter<PoetAdapter.PoetViewHolder>(),PoemClickListener {
    class PoetViewHolder(var view: RecyclerRowPoetBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RecyclerRowPoetBinding>(inflater,R.layout.recycler_row_poet,parent,false)
        return PoetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoetViewHolder, position: Int) {
        holder.view.poet = poetList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return poetList.size
    }

    fun refreshPoetList(newPoetList:ArrayList<Poet>){
        poetList.clear()
        poetList.addAll(newPoetList)
        notifyDataSetChanged()
    }

    override fun PoemClicked(view: View) {
    }

    override fun PoetClicked(view: View) {
    }

}