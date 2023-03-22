package com.zara.rickandmortyzaratest.presenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.zara.rickandmortyzaratest.R
import com.zara.rickandmortyzaratest.databinding.CharacterItemBinding
import com.zara.rickandmortyzaratest.domain.model.CharacterDomain

/**
 * RecyclerView Adapter for Rick and Morty character List
 */

class CharacterAdapter(private val onClickCallback: (Int) -> Unit) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<CharacterDomain>() {
        override fun areItemsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CharacterDomain, newItem: CharacterDomain): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var characterList: List<CharacterDomain>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.character_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterList[position]
        holder.bind(character, onClickCallback)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CharacterItemBinding.bind(view)

        fun bind(character: CharacterDomain, onClick: (Int) -> Unit) {
            binding.apply {
                tvName.text = character.name
                tvSpecieAndStatus.text = "${character.species} - ${character.status}"
                Glide.with(root)
                    .load(character.image)
                    .transform(CircleCrop())
                    .into(imgAvatar)
                root.setOnClickListener { onClick(character.id) }
            }
        }
    }
}