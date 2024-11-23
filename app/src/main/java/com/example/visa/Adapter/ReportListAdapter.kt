package com.example.visa.Adapter

import android.content.Context
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.visa.Domain.BudgetDomain
import com.visa.R
import com.visa.databinding.ViewholderBudgetBinding

class ReportListAdapter(private val items:MutableList<BudgetDomain>):
    RecyclerView.Adapter<ReportListAdapter.Viewholder>() {
    class Viewholder (val binding: ViewholderBudgetBinding):RecyclerView.ViewHolder(binding.root)

    private lateinit var context:Context

    var formatter:DecimalFormat? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportListAdapter.Viewholder {
        context=parent.context
        formatter= DecimalFormat("###,###,###,###")
        val binding=ViewholderBudgetBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: ReportListAdapter.Viewholder, position: Int) {
        val item=items[position]

        holder.binding.titleTxt.text=item.title
        holder.binding.percentTxt.text="%"+item.percent
        holder.binding.priceTxt.text="$"+formatter?.format(item.price)+" /Mes"

        holder.binding.circularProgressBar.apply {
            progress=item.percent.toFloat()

            if(position%2==1){
                progressBarColor=context.resources.getColor(R.color.blue)
                holder.binding.percentTxt.setTextColor(context.resources.getColor(R.color.blue))
            } else{
                progressBarColor=context.resources.getColor(R.color.pink)
                holder.binding.percentTxt.setTextColor(context.resources.getColor(R.color.pink))
            }
        }
    }

    override fun getItemCount(): Int = items.size
}