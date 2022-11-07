package com.example.organize.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.organize.R
import com.example.organize.data.DataSource
import com.example.organize.data.DataSource.SocialMediaPlatforms
import com.example.organize.data.SocialMediaAccount
import com.example.organize.databinding.ListItem2Binding
import com.example.organize.databinding.ListItem3Binding
import com.example.organize.model.SocialMediaCard

class SocialMediaAdapter2(private val onItemClicked: (SocialMediaAccount) -> Unit): ListAdapter<SocialMediaAccount, SocialMediaAdapter2.SocialMediaViewHolder2>(DiffCallback){


    class SocialMediaViewHolder2(private var binding: ListItem3Binding):
            RecyclerView.ViewHolder(binding.root) {
                fun bind(item: SocialMediaAccount) {
                    if (item.accountIcon == 0) {
                        binding.socialMediaIcon.setImageResource(R.drawable.website_logo)
                    }else {
                        binding.socialMediaIcon.setImageResource(item.accountIcon)
                    }
                    binding.socialMediaIconName.text = item.accountTitle
                }
            }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<SocialMediaAccount>() {
            override fun areItemsTheSame(
                oldItem: SocialMediaAccount,
                newItem: SocialMediaAccount
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: SocialMediaAccount,
                newItem: SocialMediaAccount
            ): Boolean {
                return oldItem.accountTitle == newItem.accountTitle
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialMediaAdapter2.SocialMediaViewHolder2 {
        return SocialMediaAdapter2.SocialMediaViewHolder2(
            ListItem3Binding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: SocialMediaViewHolder2, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }
}