package com.example.marvelscharacters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.marvelscharacters.GlideApp
import com.example.marvelscharacters.R
import com.example.marvelscharacters.databinding.ItemComicBinding
import com.example.marvelscharacters.response.Comic
import javax.inject.Inject

class ComicsAdapter @Inject constructor() :
    RecyclerView.Adapter<ComicsAdapter.ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val binding =
            LayoutInflater.from(parent.context).inflate(R.layout.item_comic, parent, false)
        return ComicViewHolder(ItemComicBinding.bind(binding))

    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ComicViewHolder(private val binding: ItemComicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comic: Comic) {
            binding.apply {
                comicName.text = comic.title
                val comicThumbnail = comic.thumbnail?.fullPath

                GlideApp.with(itemView).load(comicThumbnail)
                    .transition(DrawableTransitionOptions.withCrossFade()).fitCenter()
                    .placeholder(R.drawable.poster_placeholder).error(R.drawable.icon_no_background)
                    .into(comicThumbnailImageView)
            }
        }
    }


    private val differCallback = object : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
}