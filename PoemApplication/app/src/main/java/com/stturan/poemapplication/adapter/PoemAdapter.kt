package com.stturan.poemapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.stturan.poemapplication.R
import com.stturan.poemapplication.model.PoemFinal
import com.stturan.poemapplication.view.PostFragmentDirections
import kotlinx.android.synthetic.main.recycler_row_poem.view.*

class PoemAdapter(val poemList: ArrayList<PoemFinal>):RecyclerView.Adapter<PoemAdapter.PoemViewHolder>() {
    class PoemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row_poem,parent,false)

        return PoemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoemViewHolder, position: Int) {
        holder.itemView.recycler_row_poem_title.text = poemList[position].poem_title
        //holder.itemView.recycler_row_poem_poet_name.text = poemList[position].poet_id.toString()
        //val _poet = getPoet(poemList[position].poet_id!!.toInt())
        holder.itemView.recycler_row_poem_poet_name.text = poemList[position].poet.poet_name

        holder.itemView.recycler_row_poem_poet_name.setOnClickListener {
            val action = PostFragmentDirections.actionPostFragmentToPoetFragment2(poemList[position].poet.poet_id,poemList[position].poet.poet_name)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.setOnClickListener {
            val action = PostFragmentDirections.actionPostFragmentToPoemFragment2(poemList[position].id)
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