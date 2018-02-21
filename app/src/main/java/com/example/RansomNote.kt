package com.example

fun canCreateReplica(magazineWords: String, ransomNote: String): String {
    val magazineWords = magazineWords.split(" ")
    val ransomNoteWords = ransomNote.split(" ")

    ransomNoteWords.forEach({ word ->
        if (magazineWords.occurrencesOf(word) < ransomNoteWords.occurrencesOf(word)) {
            return "No"
        }
    })
    return "Yes"
}

private fun List<String>.occurrencesOf(word: String): Int {
    return this.count({it == word})
}
