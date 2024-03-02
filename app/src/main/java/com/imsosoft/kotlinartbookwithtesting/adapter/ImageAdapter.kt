package com.imsosoft.kotlinartbookwithtesting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.imsosoft.kotlinartbookwithtesting.databinding.SearchRowBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor(
    val glide: RequestManager
): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>()
{


    private val diffUtil = object: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    private var images: List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    class ImageViewHolder(val binding: SearchRowBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SearchRowBinding.inflate(layoutInflater, parent, false)
        return ImageViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = holder.binding.searchArtImageRow
        val url = images[position]

        // maybe we should use holder.itemView instead of holder.binding here
        holder.binding.apply {
            glide.load(url).into(image)

            holder.itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

}