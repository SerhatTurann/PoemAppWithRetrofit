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
import com.stturan.poemapplication.adapter.PoetsPoemAdapter
import com.stturan.poemapplication.model.Poet
import com.stturan.poemapplication.viewmodel.PoetViewModel
import kotlinx.android.synthetic.main.fragment_poet.*

class PoetFragment : Fragment() {

    private lateinit var viewModel: PoetViewModel
    private val adapter = PoetsPoemAdapter(arrayListOf())
    private  var _poet :Poet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            _poet = Poet(PoetFragmentArgs.fromBundle(it).poetName,PoetFragmentArgs.fromBundle(it).poetId)
            poet_title.text = (PoetFragmentArgs.fromBundle(it).poetName)

        }

        viewModel = ViewModelProviders.of(this).get(PoetViewModel::class.java)
        viewModel.refreshData(_poet!!)


        poet_recyclerView.layoutManager = LinearLayoutManager(context)
        poet_recyclerView.adapter = adapter


        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.poetsPoems.observe(this, Observer {
            it?.let {
                poet_title.text = _poet!!.poet_name
                poet_recyclerView.visibility = View.VISIBLE
                adapter.refreshPoemList(it)
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            it?.let {
                if(it){
                    poet_recyclerView.visibility = View.GONE
                    poet_progressBar.visibility = View.GONE
                    poet_errorText.visibility = View.VISIBLE
                }else{
                    poet_errorText.visibility = View.GONE
                }
            }
        })

        viewModel.progressBar.observe(this, Observer {
            it?.let {
                if (it){
                    poet_recyclerView.visibility = View.GONE
                    poet_errorText.visibility = View.GONE
                    poet_progressBar.visibility = View.VISIBLE
                }else{
                    poet_progressBar.visibility = View.GONE
                }
            }
        })
    }

}