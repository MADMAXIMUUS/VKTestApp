package ru.madmax.vktestapp.presentation.mainScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.madmax.vktestapp.databinding.ItemGifBinding
import ru.madmax.vktestapp.domain.model.GifItem

class GifsAdapter(
    private val listener: OnClickListener
) : ListAdapter<GifItem, GifsAdapter.GifsViewHolder>(DiffCallback()) {

    inner class GifsViewHolder(private val binding: ItemGifBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GifItem) {
            Glide
                .with(binding.itemGifImage.context)
                .load(item.url)
                .into(binding.itemGifImage)

            Glide
                .with(binding.itemGifUserAvatar.context)
                .load(item.userAvatarUrl)
                .into(binding.itemGifUserAvatar)

            binding.itemGifTitle.text = item.title
            binding.itemGifUsername.text = item.username

            binding.root.setOnClickListener {
                listener.onClick(item.id)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<GifItem>() {
        override fun areItemsTheSame(oldItem: GifItem, newItem: GifItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GifItem, newItem: GifItem) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        val binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    interface OnClickListener {
        fun onClick(id: String)
    }
}