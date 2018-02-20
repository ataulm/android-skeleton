package com.example

private const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

fun minimumCharacterDeletionsToMakeAnagram(a: String, b: String): Int {
    var deletions = 0
    for (index in 0 until ALPHABET.length) {
        val letter = ALPHABET[index]
        val differenceInLetterFrequency = Math.abs(a.countMatches(letter) - b.countMatches(letter))
        deletions += differenceInLetterFrequency
    }
    return deletions
}

private fun String.countMatches(letter: Char) = this.count { it == letter }