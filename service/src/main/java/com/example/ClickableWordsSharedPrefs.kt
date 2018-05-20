package com.example

import android.content.Context
import android.content.SharedPreferences

class ClickableWordsSharedPrefs private constructor(private val sharedPrefs: SharedPreferences) {

    companion object {

        private const val FILE = "clickable_words"
        private const val KEY_CLICKABLE_WORDS_LIST = "clickable_words_list"

        fun create(context: Context): ClickableWordsSharedPrefs {
            val sharedPrefs = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)
            return ClickableWordsSharedPrefs(sharedPrefs)
        }
    }

    private val callbacks = mutableSetOf<Callback>()

    fun clickableWords(): List<ClickableWord> {
        return immutableWords().toList().map { ClickableWord(it) }
    }

    fun add(clickableWord: ClickableWord) {
        val list = immutableWords().toMutableList()
        list.add(clickableWord.word)
        persistClickableWords(list)
    }

    fun delete(clickableWord: ClickableWord) {
        val list = immutableWords().toMutableList()
        list.remove(clickableWord.word)
        persistClickableWords(list)
    }

    private fun immutableWords() = sharedPrefs.getStringSet(KEY_CLICKABLE_WORDS_LIST, emptySet())

    private fun persistClickableWords(list: MutableList<String>) {
        sharedPrefs.edit().putStringSet(KEY_CLICKABLE_WORDS_LIST, list.toSet()).apply()
    }

    fun addOnChangeListener(callback: Callback) {
        callbacks.add(callback)
        if (callbacks.size == 1) {
            sharedPrefs.registerOnSharedPreferenceChangeListener(clickableWordsChangedListener)
        }
    }

    fun removeChangeListener(callback: Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            sharedPrefs.unregisterOnSharedPreferenceChangeListener(clickableWordsChangedListener)
        }
    }

    private val clickableWordsChangedListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == KEY_CLICKABLE_WORDS_LIST) {
            val clickableWords = clickableWords()
            callbacks.forEach({
                it.onChange(clickableWords)
            })
        }
    }

    interface Callback {

        fun onChange(clickableWords: List<ClickableWord>)
    }
}
