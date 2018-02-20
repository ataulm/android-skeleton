this isn't an android project
=============================

Using this to do the challenges that are available on [HackerRank](https://www.hackerrank.com/challenges/)

## [ArrayLeftRotation](https://www.hackerrank.com/challenges/ctci-array-left-rotation/problem)

- Given a 1-dimensional array of size N, rotate the array K times.
- A single rotation shifts each element one index to the left, with the first element becoming the last element.

Did this simply:

- created a function that does one rotation
- used that function K times

Improvement: there will be a function to map oldIndex -> newIndex, such that for-loops aren't needed, and only one more array (than is given) needs to be initialized.

## [Anagrams](https://www.hackerrank.com/challenges/ctci-making-anagrams/problem)

- Given two strings, `a` and `b`, that may or may not be of the same length, determine the minimum number of character deletions required to make `a` and `b` anagrams.
- Any characters can be deleted from either of the strings.
