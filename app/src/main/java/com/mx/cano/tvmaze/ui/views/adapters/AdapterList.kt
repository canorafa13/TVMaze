package com.mx.cano.tvmaze.ui.views.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mx.cano.tvmaze.R
import com.mx.cano.tvmaze.connection.data.ScheduleResponse
import com.mx.cano.tvmaze.connection.data.SearchResponse
import com.mx.cano.tvmaze.databinding.ItemListBinding

class AdapterList(
    private val list: List<ScheduleResponse>,
    private val list2: List<SearchResponse>,
    private val isSearch: Boolean = false,
    private val context: Context,
    private val listener: (type: ScheduleResponse) -> Unit,
    private val listener2: (type: SearchResponse) -> Unit,
): RecyclerView.Adapter<AdapterList.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view){
        val binding: ItemListBinding = ItemListBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context!!).inflate(R.layout.item_list, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(isSearch){
            list2[position].show?.name?.let { holder.binding.title.text = it }
            list2[position].show?.network?.name.let { holder.binding.network.text = it }
            list2[position].show?.schedule?.time.let { holder.binding.date.text = it }
            list2[position].show?.schedule?.days.let {
                holder.binding.date.text = "${holder.binding.date.text} | $it"
            }

            list2[position].show?.image?.medium?.let {
                Glide.with(context)
                    .load(it)
                    .into(holder.binding.poster)
            }

            holder.binding.action.setOnClickListener {
                listener2(list2[position])
            }
        }else {
            list[position].show?.name?.let { holder.binding.title.text = it }
            list[position].show?.network?.name.let { holder.binding.network.text = it }
            list[position].airdate.let { holder.binding.date.text = it }
            list[position].airtime.let {
                holder.binding.date.text = "${holder.binding.date.text} | $it"
            }

            list[position].show?.image?.medium?.let {
                Glide.with(context)
                    .load(it)
                    .into(holder.binding.poster)
            }

            holder.binding.action.setOnClickListener {
                listener(list[position])
            }
        }

    }

    override fun getItemCount(): Int  = if (isSearch) list2.size else list.size

}