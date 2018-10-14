package com.example

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.view_email.view.*

internal class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(email: EmailUiModel) {
        itemView.previewTextView.text = email.preview
        itemView.setOnClickListener { email.onClick() }

        itemView.avatarView.apply {
            Glide.with(context)
                    .load(email.contactImageUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageView())
            setChecked(email.isSelected(), shouldAnimate = false)
            setOnClickListener {
                val targetState = !isChecked
                setChecked(targetState, shouldAnimate = true)
                email.setSelected(targetState)
            }
        }
    }

    companion object {

        fun inflate(parent: ViewGroup): EmailViewHolder = LayoutInflater.from(parent.context).let {
            val view = it.inflate(R.layout.view_email, parent, false)
            return EmailViewHolder(view)
        }
    }
}
