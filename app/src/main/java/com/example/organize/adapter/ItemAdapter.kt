package com.example.organize.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.organize.PasswordCategoriesFragmentDirections
import com.example.organize.R
import com.example.organize.data.DataSource.Categories
import com.example.organize.model.PasswordCategory


class ItemAdapter(
    private val context: Context
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // TODO: Initialize the data using the List found in data/DataSource
    private val dataset: List<PasswordCategory> = Categories
// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder.
// Each data item is just an Category.

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.category_name)
        val imageView: ImageView = view.findViewById(R.id.category_image)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val resources = context.resources
        holder.textView.text = resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.ImageResourceID)

        holder.itemView.setOnClickListener {
            val categoryName = item.stringResourceId

            val action = when (categoryName) {
                R.string.bank_category -> {
                    PasswordCategoriesFragmentDirections.actionPasswordCategoriesFragmentToBankCategoryFragment()
                }
                R.string.social_media_category -> PasswordCategoriesFragmentDirections.actionPasswordCategoriesFragmentToSocialMediaCategoryFragment()
                else -> PasswordCategoriesFragmentDirections.actionPasswordCategoriesFragmentToEmailCategoryFragment()
            }
            holder.view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = dataset.size
}
