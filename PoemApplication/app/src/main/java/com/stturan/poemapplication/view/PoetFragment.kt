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
import com.stturan.poemapplication.viewmodel.PoetViewModel
import kotlinx.android.synthetic.main.fragment_poet.*

class PoetFragment : Fragment() {

    private lateinit var viewModel: PoetViewModel
    private val adapter = PoetsPoemAdapter(arrayListOf())
    var poet_id : String = "-1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_poet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            poet_id = PoetFragmentArgs.fromBundle(it).poetId
        }

        viewModel = ViewModelProviders.of(this).get(PoetViewModel::class.java)
        viewModel.refreshData(poet_id)


        poet_recyclerView.layoutManager = LinearLayoutManager(context)
        poet_recyclerView.adapter = adapter


        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.poetsPoems.observe(viewLifecycleOwner, Observer {
            it?.let {
                poet_recyclerView.visibility = View.VISIBLE
                adapter.refreshPoemList(it)
            }
        })

        viewModel.poet.observe(viewLifecycleOwner, Observer {
            it?.let {
                poet_title.text = it.poet_name
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
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

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
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