package com.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ConfigureEntriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configure_entries)

        /*
        Keep single StringSet denoting list of words
        Add packages for given word as new StringSets (word:set of package names)
        Deleting a word must cascade down into deleting the config for that word
        Loading configuration must check for install status of package
        No packages for a given word? Word is inactive (but present).
         */
    }

    private class PackageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        companion object {

            fun inflate(parent: ViewGroup):PackageViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_package, parent, false)
                return PackageViewHolder(view)
            }
        }
    }
}
