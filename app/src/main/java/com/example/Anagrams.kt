package com.example

class Anagrams {

    companion object {

        private const val ALPHABET = "abcdefghijklmnopqrstuvwxyz"

        fun minimumCharacterDeletionsToMakeAnagram(a: String, b: String): Int {
            var deletions = 0
            for (index in 0 until ALPHABET.length) {
                val letter = ALPHABET[index]
                val differenceInLetterFrequency = Math.abs(a.count(matches(letter)) - b.count(matches(letter)))
                deletions += differenceInLetterFrequency
            }
            return deletions
        }

        private fun matches(letter: Char): (Char) -> Boolean {
            return { it: Char -> it == letter }
        }
    }
}
