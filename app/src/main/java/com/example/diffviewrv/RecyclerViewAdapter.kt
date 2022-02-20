package com.example.diffviewrv

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.diffviewrv.Data.Data

class RecyclerViewAdapter(context: Context, val list: ArrayList<Data>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_ACTIVITY = 1
        const val VIEW_ADD_BUTTON = 2 }

    private val context: Context = context
    private lateinit var mListener: onItemClickListener


    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_ACTIVITY) {
            return View1ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_activity, parent, false),mListener)
        }
        return View2ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_add_button, parent, false),mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].viewType === VIEW_ACTIVITY) {
            val recyclerViewModel = list[position]
            (holder as View1ViewHolder).bind(recyclerViewModel)
        } else {
            val recyclerViewModel = list[position]
            (holder as View2ViewHolder).bind(recyclerViewModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }

    override fun getItemCount(): Int {
        return list.size
    }

    private inner class View1ViewHolder(itemView: View, listener: onItemClickListener) :  RecyclerView.ViewHolder(itemView) {
        val tvSub= itemView.findViewById<TextView>(R.id.tvSub)
        val tvdayDateLayoutRev = itemView.findViewById<ConstraintLayout>(R.id.dayDateLayout)
        val tvDuration = itemView.findViewById<TextView>(R.id.tvDuration)
        val tvDone = itemView.findViewById<TextView>(R.id.tvDone)
        val tvdate = itemView.findViewById<TextView>(R.id.tvdate)
        val tvDay = itemView.findViewById<TextView>(R.id.tvDay)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.btnDelete)



        init{
            itemView.setOnClickListener {
                listener.onItemClick( adapterPosition)
            }
            btnDelete.setOnClickListener {
                list.removeAt(position)
                notifyItemRemoved(position)
                Log.e("positionD",position.toString())
            }
        }

        fun bind(data: Data) {
            tvdayDateLayoutRev.isInvisible = data.dayDatevisibilityRev
            tvSub.text = data.textTitle
            tvDuration.text = data.textDuration
            tvDone.text = data.textDone
            tvdate.text = data.textMainDate
            tvDay.text = data.textWeekDayName

        }
    }

    private inner class View2ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val dayDateLayoutRev = itemView.findViewById<ConstraintLayout>(R.id.dayDateLayout)

        init{
            itemView.setOnClickListener {
                listener.onItemClick( adapterPosition)
            }
        }
        fun bind(data: Data) {
            dayDateLayoutRev.isInvisible = data.dayDatevisibilityRev
        }}


}
