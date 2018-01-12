package com.example.kotlinfastnetworking.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.kotlinfastnetworking.R
import com.example.kotlinretrofit.model.Photo

/**
 * Created by nikhil.jadhav on 30-11-2017.
 */
class RvAdapter(val photoList: List<Photo>?,
                val clickListener: View.OnClickListener): RecyclerView.Adapter<RvAdapter.PhotoViewHolder>() {

    override fun onBindViewHolder(holder: PhotoViewHolder?, position: Int) {
        val photo:Photo = photoList!![position]
        holder?.bindItem(photo)
    }

    override fun getItemCount(): Int {
        return photoList!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.item_view,parent,false))
    }

    //region Use this for getiing the current position for adapterr item
    fun getPhoto(adaperPosition : Int):Photo{
        return photoList!!.get(adaperPosition)
    }
    //endregion


    inner class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var likes:TextView
        var photo_item:ImageView

        fun bindItem(photo:Photo){
            likes.text = photo.likes.toString()

            if(photo.previewURL.isNotEmpty()){
                Glide.with(likes.context)
                        .load(photo.previewURL)
                        .thumbnail(0.5f)
                        .into(photo_item)
            }
        }

        init {
            itemView.setOnClickListener(clickListener)
            itemView.tag = this
            likes = itemView.findViewById(R.id.tvLikes)
            photo_item = itemView.findViewById(R.id.ivMain)
        }

    }
}