package com.mx.cano.tvmaze.ui.views.fragments

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.cano.tvmaze.R
import com.mx.cano.tvmaze.base.FragmentBase
import com.mx.cano.tvmaze.connection.data.ScheduleResponse
import com.mx.cano.tvmaze.databinding.FragmentMainBinding
import com.mx.cano.tvmaze.ui.views.adapters.AdapterList
import com.mx.cano.tvmaze.ui.views.viewmodels.ViewModelMain

class FragmentMain: FragmentBase<FragmentMainBinding>(R.layout.fragment_main)  {
    private lateinit var viewModel: ViewModelMain
    override fun getBinding(view: View) = FragmentMainBinding.bind(view)

    override fun setupFragment() {
        viewModel = ViewModelProvider(this).get(ViewModelMain::class.java)




        binding.list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }


        binding.fecha.text = getCurrentDate()

        viewModel.getSchedule(getCurrentDate()).observe(this) {
            it?.let {
                binding.list.apply {
                    adapter = AdapterList(it, requireContext()){
                        onSelect(it)
                    }
                }
            }
        }


        binding.search.setOnClickListener {
            binding.apply {
                fecha.visibility = View.GONE
                search.visibility = View.GONE
                query.visibility = View.VISIBLE
                close.visibility = View.VISIBLE
            }
        }

        binding.close.setOnClickListener {
            binding.apply {
                fecha.visibility = View.VISIBLE
                search.visibility = View.VISIBLE
                query.visibility = View.GONE
                close.visibility = View.GONE
                query.setText("")
            }
        }

        binding.query.addTextChangedListener {

        }
    }

    private fun onSelect(scheduleResponse: ScheduleResponse){

    }


    private fun getCurrentDate(): String{
        return "2022-07-15"
    }
}
