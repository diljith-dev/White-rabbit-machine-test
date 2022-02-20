package com.diljith.whiterabbit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.diljith.whiterabbit.R
import com.diljith.whiterabbit.model.Employee
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_employee_item.view.*


class EmployeeListAdapter(
    private val employeeList: ArrayList<Employee>,
    private var context: Context,
    private val OnItemClickInterFace: OnItemClick,
) : RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_employee_item, parent, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(employeeList[position])
        holder.itemView.tvName.text = employeeList[position].name
        holder.itemView.tvCompanyName.text = "Company :" + employeeList[position].company?.name

        val builder = Picasso.Builder(context)
        builder.listener { picasso, uri, exception ->

            exception.printStackTrace()
        }
        builder.build()
            .load(employeeList[position].profile_image)
            .error(R.drawable.ic_baseline_camera_alt_24)
            .placeholder(R.drawable.ic_baseline_camera_alt_24)
            .into(holder.itemView.imageView, object : Callback.EmptyCallback() {
                override fun onSuccess() {}
                override fun onError() {}
            })

    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(get: Employee) {
            itemView.setOnClickListener { OnItemClickInterFace.onItemClick(employeeList[adapterPosition]) }
        }

    }

    interface OnItemClick {
        fun onItemClick(data: Employee)
    }
}