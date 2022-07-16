package com.mx.cano.tvmaze.ui.views.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mx.cano.tvmaze.DroidApp
import com.mx.cano.tvmaze.connection.data.ScheduleResponse
import com.mx.cano.tvmaze.connection.data.SearchResponse
import kotlinx.coroutines.launch

class ViewModelMain: ViewModel() {

    fun getSchedule(date: String): MutableLiveData<List<ScheduleResponse>?>{
        val data = MutableLiveData<List<ScheduleResponse>?>()
        viewModelScope.launch {
            val result = DroidApp.getRetrofitTVMAZE().schedule(date = date)
            if (result.isSuccessful){
                data.postValue(result.body())
            }else{
                data.postValue(null)
            }
        }
        return data
    }


    fun getSearchShow(query: String): MutableLiveData<List<SearchResponse>?>{
        val data = MutableLiveData<List<SearchResponse>?>()
        viewModelScope.launch {
            val result = DroidApp.getRetrofitTVMAZE().searchShows(query)
            if (result.isSuccessful){
                data.postValue(result.body())
            }else{
                data.postValue(null)
            }
        }
        return data
    }
}