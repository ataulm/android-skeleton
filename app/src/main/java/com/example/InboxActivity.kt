package com.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_inbox.*

private const val KEY_STATE = "presenter-state"

internal class InboxActivity : AppCompatActivity(), Presenter.View {

    private val presenter = Presenter()
    private val inboxAdapter = InboxAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)
        inboxRecyclerView.adapter = inboxAdapter
        presenter.startPresenting(this, savedInstanceState?.getParcelable(KEY_STATE))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(KEY_STATE, presenter.state())
        super.onSaveInstanceState(outState)
    }

    override fun display(emails: List<EmailUiModel>) = inboxAdapter.submitList(emails)

    override fun openEmail(emailId: String) =
            Toast.makeText(this, "onClick email: $emailId", Toast.LENGTH_SHORT).show()
}
