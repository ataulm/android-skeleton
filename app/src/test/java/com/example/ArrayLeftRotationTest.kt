package com.example

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ArrayLeftRotationTest {

    @Test
    fun rotateOnce() {
        val input = listOf(1, 2,  3, 4, 5)

        val rotated = ArrayLeftRotation.Companion.leftRotate(input, 1)

        assertThat(rotated).isEqualTo(listOf(2, 3, 4, 5, 1))
    }

    @Test
    fun rotateTwice() {
        val input = listOf(1, 2, 3, 4, 5)

        val rotated = ArrayLeftRotation.Companion.leftRotate(input, 2)

        assertThat(rotated).isEqualTo(listOf(3, 4, 5, 1, 2))
    }

    @Test
    fun rotateEntireLengthOfList() {
        val input = listOf(1, 2, 3, 4, 5)

        val rotated = ArrayLeftRotation.Companion.leftRotate(input, input.size)

        assertThat(rotated).isEqualTo(input)
    }
}
