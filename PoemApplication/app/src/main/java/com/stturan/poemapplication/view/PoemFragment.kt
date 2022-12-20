package com.stturan.poemapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.stturan.poemapplication.R
import com.stturan.poemapplication.adapter.PoemClickListener
import com.stturan.poemapplication.databinding.FragmentPoemBinding
import com.stturan.poemapplication.viewmodel.PoemViewModel
import kotlinx.android.synthetic.main.fragment_poem.*

class PoemFragment : Fragment(),PoemClickListener {

    private lateinit var viewModel: PoemViewModel
    private var poem_id: Int =-1

    private lateinit var binding: FragmentPoemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_poem, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        arguments?.let {
            poem_id = PoemFragmentArgs.fromBundle(it).poemId
        }
        viewModel = ViewModelProviders.of(this).get(PoemViewModel::class.java)
        viewModel.refreshData(poem_id.toString())

        binding.mcontext = this
        binding.listener = this

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.poem.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.poem = it
            }

        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {

                if(it){
                    poem_progressBar.visibility = View.GONE
                    poem_errorText.visibility = View.VISIBLE
                }else{
                    poem_errorText.visibility = View.GONE
                }
            }
        })

        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
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

    override fun PoemClicked(view: View) {
    }

    override fun PoetClicked(view: View) {
        val action = PoemFragmentDirections.actionPoemFragmentToPoetFragment2(binding.frgPoemPoetId.text.toString())
        Navigation.findNavController(view).navigate(action)
    }


}