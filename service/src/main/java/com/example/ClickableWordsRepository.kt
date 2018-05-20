package com.example

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class ClickableWordsRepository(private val sharedPrefs: ClickableWordsSharedPrefs) {

    fun clickableWords(): LiveData<List<ClickableWord>> {
        return ClickableWordsLiveData(sharedPrefs)
    }

    fun add(clickableWord: ClickableWord) {
        sharedPrefs.add(clickableWord)
    }

    fun delete(clickableWord: ClickableWord) {
        sharedPrefs.delete(clickableWord)
    }

    class ClickableWordsLiveData(private val sharedPrefs: ClickableWordsSharedPrefs) : MutableLiveData<List<ClickableWord>>() {

        override fun onActive() {
            super.onActive()
            value = sharedPrefs.clickableWords()
            sharedPrefs.addOnChangeListener(callback)
        }

        override fun onInactive() {
            sharedPrefs.removeChangeListener(callback)
            super.onInactive()
        }

        private val callback = object : ClickableWordsSharedPrefs.Callback {
            override fun onChange(clickableWords: List<ClickableWord>) {
                value = clickableWords
            }
        }
    }
}
