package com.example

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RansomNoteTest {

    @Test
    fun possible() {
        val result = canCreateReplica("give me one grand today night", "give one grand today")

        assertThat(result).isEqualTo("Yes")
    }

    @Test
    fun notPossible() {
        val result = canCreateReplica("two times three is not four", "two times two is four")

        // because "two" only appears once and "not" does not appear at all
        assertThat(result).isEqualTo("No")
    }
}