package com.example

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class AnagramsTest {

    @Test
    fun cde_abc() {
        val result = Anagrams.Companion.minimumCharacterDeletionsToMakeAnagram("cde", "abc")

        assertThat(result).isEqualTo(4)
    }

    @Test
    fun abc_abc() {
        val result = Anagrams.Companion.minimumCharacterDeletionsToMakeAnagram("abc", "abc")

        assertThat(result).isEqualTo(0)
    }
}
