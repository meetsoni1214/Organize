package com.example.organize.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.organize.R
import com.example.organize.data.BankAccount
import com.example.organize.databinding.ListItem4Binding

class BankAccountAdapter(private val onItemClicked: (BankAccount) -> Unit): ListAdapter<BankAccount, BankAccountAdapter.BankAccountViewHolder>(DiffCallback)  {

    class BankAccountViewHolder(private var binding: ListItem4Binding):
            RecyclerView.ViewHolder(binding.root) {
                fun bind(item: BankAccount) {
                    when(item.bankName) {
                        "Bank of Baroda" -> {
                            binding.bankLogo2.setImageResource(R.drawable.bank_of_baroda_logo)
                        }
                        "The Kalupur Commercial Co-operative Bank" -> {
                            binding.bankLogo2.setImageResource(R.drawable.kalupur_bank_logo)
                        }
                        "State Bank of India" -> {
                            binding.bankLogo2.setImageResource(R.drawable.sbi_logo)
                        }
                        "Bank of India" -> {
                            binding.bankLogo2.setImageResource(R.drawable.boi)
                        }
                        "Union Bank of India" -> {
                            binding.bankLogo2.setImageResource(R.drawable.union_bank)
                        }
                        "ICICI Bank" -> {
                            binding.bankLogo2.setImageResource(R.drawable.icici_logo)
                        }
                        else ->{
                        binding.bankLogo2.setImageResource(R.drawable.bank_image_2)
                    }
                    }
                    binding.title.text = item.bankName
                    binding.holderName.text = item.accountHolderName
                }
            }

    companion object {
        private val DiffCallback = object  : DiffUtil.ItemCallback<BankAccount>() {
            override fun areContentsTheSame(oldItem: BankAccount, newItem: BankAccount): Boolean {
                return oldItem.accountNo == newItem.accountNo
            }

            override fun areItemsTheSame(oldItem: BankAccount, newItem: BankAccount): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankAccountViewHolder {
        return BankAccountViewHolder(
            ListItem4Binding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: BankAccountViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }
}