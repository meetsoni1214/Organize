package com.example.organize.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.organize.data.EmailAccount
import androidx.recyclerview.widget.ListAdapter
import com.example.organize.R
import com.example.organize.databinding.ListItem3Binding

class EmailListAdapter(private val onItemClicked: (EmailAccount) -> Unit): ListAdapter<EmailAccount, EmailListAdapter.EmailViewHolder>(DiffCallback){


    class EmailViewHolder(private var binding: ListItem3Binding):
            RecyclerView.ViewHolder(binding.root) {
                fun bind(item: EmailAccount) {
                    binding.apply {
                        socialMediaIcon.setImageResource(R.drawable.email_icon)
                        socialMediaIconName.text = item.accountTitle
                    }
                }
            }
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<EmailAccount>() {
            override fun areItemsTheSame(oldItem: EmailAccount, newItem: EmailAccount): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: EmailAccount, newItem: EmailAccount): Boolean {
                return oldItem.accountEmail == newItem.accountEmail
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        return EmailViewHolder(
            ListItem3Binding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }
}