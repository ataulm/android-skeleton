package com.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_inbox.*
import kotlinx.android.synthetic.main.view_email.view.*

internal class InboxActivity : AppCompatActivity(), Presenter.View {

    private val presenter = Presenter()
    private val inboxAdapter = InboxAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)
        inboxRecyclerView.adapter = inboxAdapter
        presenter.startPresenting(this)
    }

    override fun display(emails: List<EmailUiModel>) = inboxAdapter.submitList(emails)

    override fun openEmail(emailId: String) =
            Toast.makeText(this, "onClick email: $emailId", Toast.LENGTH_SHORT).show()
}

private class InboxAdapter : ListAdapter<EmailUiModel, EmailViewHolder>(EmailDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmailViewHolder.inflate(parent)

    override fun onBindViewHolder(viewHolder: EmailViewHolder, position: Int) = viewHolder.bind(getItem(position))
}

private class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

private object EmailDiffer : DiffUtil.ItemCallback<EmailUiModel>() {

    override fun areItemsTheSame(oldItem: EmailUiModel, newItem: EmailUiModel) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: EmailUiModel, newItem: EmailUiModel) = oldItem == newItem
}
