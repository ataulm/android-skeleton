package com.example

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup

internal class InboxAdapter : ListAdapter<EmailUiModel, EmailViewHolder>(EmailDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmailViewHolder.inflate(parent)

    override fun onBindViewHolder(viewHolder: EmailViewHolder, position: Int) = viewHolder.bind(getItem(position))

    object EmailDiffer : DiffUtil.ItemCallback<EmailUiModel>() {

        override fun areItemsTheSame(oldItem: EmailUiModel, newItem: EmailUiModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: EmailUiModel, newItem: EmailUiModel) = oldItem == newItem
    }
}
