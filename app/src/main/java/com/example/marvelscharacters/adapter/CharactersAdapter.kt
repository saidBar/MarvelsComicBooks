package com.example.marvelscharacters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.marvelscharacters.R
import javax.inject.Inject
import com.example.marvelscharacters.databinding.ItemCharacterBinding
import com.example.marvelscharacters.response.Character

class CharactersAdapter @Inject constructor()  : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private lateinit var binding: ItemCharacterBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding= ItemCharacterBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set(differ.currentList[position])
        holder.setIsRecyclable(false)
    }


    inner class ViewHolder(): RecyclerView.ViewHolder(binding.root){
        fun set(item: Character){
            binding.apply {
                characterName.text= item.name
                val characterImage = item.thumbnail?.fullPath
                Glide.with(itemView)
                    .load(characterImage)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(binding.characterImageView)

            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)
}