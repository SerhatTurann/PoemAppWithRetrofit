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
import com.stturan.poemapplication.adapter.PoemAdapter
import com.stturan.poemapplication.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.fragment_post.*

class PostFragment : Fragment() {

    private lateinit var viewModel: PostViewModel
    private val adapter = PoemAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        viewModel.refreshData()

        post_recyclerView.layoutManager = LinearLayoutManager(context)
        post_recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            post_progressBar.visibility = View.VISIBLE
            post_errorText.visibility = View.GONE
            post_recyclerView.visibility = View.GONE

            viewModel.refreshData()

            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()


    }

    fun observeLiveData(){
        viewModel.poems.observe(viewLifecycleOwner, Observer {
            it?.let {
                post_recyclerView.visibility = View.VISIBLE
                adapter.refreshPoemList(it)
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    post_recyclerView.visibility = View.GONE
                    post_progressBar.visibility = View.GONE
                    post_errorText.visibility = View.VISIBLE
                }else{
                    post_errorText.visibility = View.GONE
                }
            }
        })

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    post_recyclerView.visibility = View.GONE
                    post_errorText.visibility = View.GONE
                    post_progressBar.visibility = View.VISIBLE
                }else{
                    post_progressBar.visibility = View.GONE
                }
            }
        })
    }

}