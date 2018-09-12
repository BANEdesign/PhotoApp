package com.example.bryonnabaines.photoapp

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.bryonnabaines.photoapp.models.Photo
import kotlinx.android.synthetic.main.photo_item.view.*

/**
 * Created by bryonnabaines on 1/2/18.
 * this is the adapter for the model data and the view, as well as the
 * view holder for RecyclerView.
 * Loads and displays images
 */
//Right after the class name is the constructor followed by the class
//that it is a subclass of
class MainAdapter(var photos:MutableList<Photo> = mutableListOf(),
                  var clickListener: View.OnClickListener?) :
        RecyclerView.Adapter<MainAdapter.PhotoViewHolder>() {

    override fun onBindViewHolder(holder: PhotoViewHolder?, position: Int) {
        val photo = photos[position]
        holder?.tags?.text = photo.tags
        //Added null checks to see if photo is just a placeholder
        if(photo.id == "") {
            holder?.likesLabel?.text = ""
            holder?.likes?.text = ""
            holder?.favoritesLabel?.text = ""
            holder?.favorites?.text = ""
            holder?.progressSpinner?.visibility = View.VISIBLE
        }else {
            holder?.likes?.text = "${photo.likes}"
            holder?.favorites?.text = "${photo.favorites}"
            holder?.progressSpinner?.visibility = View.GONE
        }

        if(photo.previewURL.isNotEmpty()){
            Glide.with(holder?.tags?.context).load(photo.previewURL)
                    .into(holder?.photoItem)
        }else{
            holder?.photoItem?.setImageBitmap(null)
        }
    }

    fun addLoadingFooter(){
        photos.add(Photo("",null, null, "", "", ""))
    }

    fun dismissLoadingFooter(){

        val position = itemCount -1
        if(!photos.isEmpty() && getPhoto(position).id == "")
            photos.removeAt(position)
        notifyItemRemoved(position)
    }

    //A function to get a specif photo
    fun getPhoto(adapterPosition: Int) : Photo {
        return photos[adapterPosition]
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PhotoViewHolder {

        return PhotoViewHolder(LayoutInflater.from(parent?.context)
            .inflate(R.layout.photo_item, parent, false))
        //This will create a new photoView holder class with the layout
        //inflated into a view. Notice the parent: is nullable-this is a
        //safe way to access variables that could be null
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class PhotoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        var tags = itemView.tags
        var likesLabel = itemView.like_label
        var likes = itemView.likes
        var favoritesLabel = itemView.favorite_label
        var favorites = itemView.favorites
        var photoItem = itemView.photo_item
        val progressSpinner = itemView.progressSpinner

        //init says if clicked, set itemView
        init {
            if(clickListener != null){
                itemView.setOnClickListener(clickListener)
            }
            itemView.tag = this
        }
    }
}