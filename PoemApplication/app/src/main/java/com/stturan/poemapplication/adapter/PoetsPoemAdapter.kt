package com.stturan.poemapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.stturan.poemapplication.R
import com.stturan.poemapplication.databinding.RecyclerRowPoetsPoemBinding
import com.stturan.poemapplication.model.PoemFinal
import com.stturan.poemapplication.view.PoetFragmentDirections
import kotlinx.android.synthetic.main.recycler_row_poets_poem.view.*

class PoetsPoemAdapter (val poemList: ArrayList<PoemFinal>): RecyclerView.Adapter<PoetsPoemAdapter.PoemViewHolder>(),PoemClickListener {
    class PoemViewHolder(var view: RecyclerRowPoetsPoemBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RecyclerRowPoetsPoemBinding>(inflater,R.layout.recycler_row_poets_poem,parent,false)
        return PoemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoemViewHolder, position: Int) {
        holder.view.poem = poemList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return poemList.size
    }

    fun refreshPoemList(newPoemList:ArrayList<PoemFinal>){
        poemList.clear()
        poemList.addAll(newPoemList)
        notifyDataSetChanged()
    }

    override fun PoemClicked(view: View) {
        val action = PoetFragmentDirections.actionPoetFragmentToPoemFragment(view.poets_poem_poem_id.text.toString().toInt())
        Navigation.findNavController(view).navigate(action)
    }

    override fun PoetClicked(view: View) {
    }

}