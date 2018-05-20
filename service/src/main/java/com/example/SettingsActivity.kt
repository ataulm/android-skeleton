package com.example

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val repository = ClickableWordsRepository(ClickableWordsSharedPrefs.create(this))
        clickableWordsRecyclerView.layoutManager = LinearLayoutManager(this)
        clickableWordsRecyclerView.adapter = ClickableWordsAdapter(object : ClickableWordsAdapter.Callback {

            override fun onClickDelete(word: ClickableWord) {
                repository.delete(word)
            }
        })

        addClickableWordButton.setOnClickListener({
            val text = addClickableWordEditText.text
            if (text.isNotBlank()) {
                val clickableWord = ClickableWord(text.trim().toString())
                repository.add(clickableWord)
                addClickableWordEditText.text = null
            }
        })

        repository.clickableWords().observe(this, Observer<List<ClickableWord>> {
            if (it != null) {
                (clickableWordsRecyclerView.adapter as ClickableWordsAdapter).update(it)
            }
        })
    }
}

