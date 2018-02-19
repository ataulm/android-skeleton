package com.example

class ArrayLeftRotation {

    companion object {

        fun leftRotate(list: List<Int>, times: Int): List<Int> {
            var rotated = list
            for (i in 0 until times) {
                rotated = singleLeftRotate(rotated)
            }
            return rotated
        }

        private fun singleLeftRotate(list: List<Int>): List<Int> {
            val mutable = list.toMutableList()
            val temp = mutable[0]
            for (i in 1..mutable.lastIndex) {
                mutable[i - 1] = mutable[i]
            }
            mutable[mutable.lastIndex] = temp
            return mutable.toList()
        }
    }
}
