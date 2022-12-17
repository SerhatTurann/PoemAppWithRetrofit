package com.stturan.poemapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.stturan.poemapplication.R
import com.stturan.poemapplication.viewmodel.PoemViewModel
import kotlinx.android.synthetic.main.fragment_poem.*

class PoemFragment : Fragment() {

    private lateinit var viewModel: PoemViewModel
    private var poem_id: Int =-1
    private var _poet_id : String = "-1"
    private var _poet_name : String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        arguments?.let {
            poem_id = PoemFragmentArgs.fromBundle(it).poemId
        }
        viewModel = ViewModelProviders.of(this).get(PoemViewModel::class.java)
        viewModel.refreshData(poem_id.toString())

        observeLiveData()

        poetBox.setOnClickListener {
            val action = PoemFragmentDirections.actionPoemFragmentToPoetFragment2(_poet_id,_poet_name)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun observeLiveData(){
        viewModel.poem.observe(this, Observer {
            it?.let {
                poem_title.text = it.poem_title
                poem_text.text = it.poem_text
                poet_name.text = it.poet.poet_name

                _poet_id = it.poet.poet_id
                _poet_name = it.poet.poet_name
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            it?.let {
                if(it){
                    poem_progressBar.visibility = View.GONE
                    poem_errorText.visibility = View.VISIBLE
                }else{
                    poem_errorText.visibility = View.GONE
                }
            }
        })

        viewModel.progressBar.observe(this, Observer {
            it?.let {
                if (it){
                    poem_errorText.visibility = View.GONE
                    poem_progressBar.visibility = View.VISIBLE
                }else{
                    poem_progressBar.visibility = View.GONE
                }
            }
        })
    }

}