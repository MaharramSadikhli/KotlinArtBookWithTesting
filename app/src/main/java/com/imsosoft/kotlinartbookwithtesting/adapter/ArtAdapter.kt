package com.imsosoft.kotlinartbookwithtesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.imsosoft.kotlinartbookwithtesting.R
import com.imsosoft.kotlinartbookwithtesting.databinding.MainRowBinding
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtEntity
import javax.inject.Inject

class ArtAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ArtAdapter.ArtViewHolder>()
{

    class ArtViewHolder(val binding: MainRowBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<ArtEntity>() {
        override fun areItemsTheSame(oldItem: ArtEntity, newItem: ArtEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtEntity, newItem: ArtEntity): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var arts: List<ArtEntity>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainRowBinding.inflate(layoutInflater, parent, false)
        return ArtViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ArtViewHolder, position: Int) {
        val image = holder.binding.artRowImageView
        val name = holder.binding.artRowNameText
        val artistName = holder.binding.artRowArtistText
        val year = holder.binding.artRowYearText
        val art = arts[position]

        val context = holder.itemView.context

        val nameString = context.getString(R.string.art_name, art.name)
        val artistNameString = context.getString(R.string.artist_name, art.artistName)
        val yearString = context.getString(R.string.year, art.year.toString())

        holder.binding.apply {
            glide.load(art.imageUrl).into(image)
            name.text = nameString
            artistName.text = artistNameString
            year.text = yearString
        }
    }

    override fun getItemCount(): Int {
        return arts.size
    }

}