package com.example

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LinkedListTest {

    @Test
    fun singleNode() {
        val node = Node(1, null)

        val result = containsCycle(node)

        assertThat(result).isFalse()
    }

    @Test
    fun cycleList() {
        val c = Node(3, null)
        val b = Node(2, c)
        val a = Node(1, b)
        c.next = b

        val result = containsCycle(a)

        assertThat(result).isTrue()
    }
}
