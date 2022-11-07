package com.example.organize.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.organize.R
import com.example.organize.SocialMediaCategoryFragmentDirections
import com.example.organize.data.DataSource.SocialMediaPlatforms
import com.example.organize.model.SocialMediaCard

class SocialMediaAdapter(private val context: Context) :
    RecyclerView.Adapter<SocialMediaAdapter.SocialMediaViewHolder>() {

    private val dataset: List<SocialMediaCard> = SocialMediaPlatforms

    class SocialMediaViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.social_media_icon_name)
        val imageView: ImageView = view.findViewById(R.id.social_media_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocialMediaViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_2, parent, false)
        return SocialMediaViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: SocialMediaViewHolder, position: Int) {
        val item = dataset[position]
        val resources = context.resources

        holder.textView.text = resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.ImageResourceID)

        holder.itemView.setOnClickListener {
            val platformName = item.stringResourceId

            val action = when (platformName) {
                R.string.facebook -> SocialMediaCategoryFragmentDirections.actionSocialMediaCategoryFragmentToFacebookFragment()
                R.string.instagram -> SocialMediaCategoryFragmentDirections.actionSocialMediaCategoryFragmentToInstagramFragment()
                R.string.snapchat -> SocialMediaCategoryFragmentDirections.actionSocialMediaCategoryFragmentToSnapchatFragment()
                R.string.twitter -> SocialMediaCategoryFragmentDirections.actionSocialMediaCategoryFragmentToTwitterFragment()
                else -> SocialMediaCategoryFragmentDirections.actionSocialMediaCategoryFragmentToLinkedInFragment()
            }

            holder.view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}