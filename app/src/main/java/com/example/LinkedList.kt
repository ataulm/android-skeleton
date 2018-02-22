package com.example

fun containsCycle(head: Node): Boolean {
    val visited = HashSet<Node>()
    visited.add(head)
    var current = head
    while (current.next != null) {
        if (visited.contains(current.next!!)) {
            return true
        }
        current = current.next!!
        visited.add(current)
    }
    return false
}

class Node(val data: Int, var next: Node?)
