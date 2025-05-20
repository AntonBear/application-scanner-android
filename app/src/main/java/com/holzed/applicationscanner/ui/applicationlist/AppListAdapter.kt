package com.holzed.applicationscanner.ui.applicationlist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.holzed.applicationscanner.data.model.AppItemModel
import com.holzed.applicationscanner.ui.card.AppItemView

class AppListAdapter(private val onClick: (AppItemModel) -> Unit) :
    ListAdapter<AppItemModel, AppListAdapter.ApplicationViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val view = AppItemView(parent.context)
        view.layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        return ApplicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    class ApplicationViewHolder(private val cardView: AppItemView) : RecyclerView.ViewHolder(cardView) {
        fun bind(item: AppItemModel) {
            cardView.configure(item)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<AppItemModel>() {
            override fun areItemsTheSame(oldItem: AppItemModel, newItem: AppItemModel): Boolean {
                return oldItem.title == newItem.title && oldItem.apkHash == newItem.apkHash
            }

            override fun areContentsTheSame(oldItem: AppItemModel, newItem: AppItemModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
