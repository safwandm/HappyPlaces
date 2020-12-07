package com.example.happyplaces.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happyplaces.R
import com.example.happyplaces.activity.AddActivity
import com.example.happyplaces.activity.MainActivity
import com.example.happyplaces.database.DatabaseHandler
import com.example.happyplaces.model.HappyPlaceModel
import kotlinx.android.synthetic.main.item_happy_places.view.*

open class HappyPlacesAdapter(
    private val context: Context,
    private val list:ArrayList<HappyPlaceModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_happy_places,
                parent,
                false
            )
        )
    }

    fun notifyEditItem(activity: Activity,position: Int,requestCode: Int){
        val intent = Intent(context,AddActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS,list[position])
        activity.startActivityForResult(
            intent,
            requestCode
        )
        notifyItemChanged(position)
    }
    fun removeAt(position: Int){
        val dbHandler = DatabaseHandler(context)
        val isDelete =dbHandler.deleteHappyPlace(list[position])
        if (isDelete > 0){
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private var onClickListener : OnClickListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model= list[position]

        if (holder is MyViewHolder){
            holder.itemView.iv_place_image.setImageURI(Uri.parse(model.image))
            holder.itemView.tvTitle.text = model.title
            holder.itemView.tvDescription.text = model.description

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: HappyPlaceModel)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    private class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

}