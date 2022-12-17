package com.stturan.poemapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stturan.poemapplication.R
import com.stturan.poemapplication.adapter.PoetAdapter
import com.stturan.poemapplication.viewmodel.PoetsViewModel
import kotlinx.android.synthetic.main.fragment_poets.*

class PoetsFragment : Fragment() {

    private lateinit var viewModel: PoetsViewModel
    private val adapter = PoetAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PoetsViewModel::class.java)
        viewModel.refreshData()

        poets_recyclerView.layoutManager = LinearLayoutManager(context)
        poets_recyclerView.adapter = adapter

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.poets.observe(this, Observer {
            it?.let {
                poets_recyclerView.visibility = View.VISIBLE
                adapter.refreshPoetList(it)
            }
        })
        viewModel.errorMessage.observe(this, Observer {
            it?.let {
                if(it){
                    poets_recyclerView.visibility = View.GONE
                    poets_progressBar.visibility = View.GONE
                    poets_errorText.visibility = View.VISIBLE
                }else{
                    poets_errorText.visibility = View.GONE
                }
            }
        })

        viewModel.progressBar.observe(this, Observer {
            it?.let {
                if (it){
                    poets_recyclerView.visibility = View.GONE
                    poets_errorText.visibility = View.GONE
                    poets_progressBar.visibility = View.VISIBLE
                }else{
                    poets_progressBar.visibility = View.GONE
                }
            }
        })
    }

}