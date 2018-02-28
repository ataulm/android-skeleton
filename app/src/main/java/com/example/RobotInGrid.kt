package com.example

class RobotInGrid {

    private val theBadPath = HashSet<Position>()

    fun findPath(maze: Array<BooleanArray>): List<Position> {
        if (!maze.isRectangular()) {
            return emptyList()
        }
        val start = Position(0, 0)
        val goal = Position(maze.size, maze.first().size)
        val workingPath = ArrayList<Position>()
        return pathToPosition(maze, start, goal, workingPath)
    }

    fun pathToPosition(maze: Array<BooleanArray>, currentPosition: Position, goal: Position, workingPath: MutableList<Position>): Boolean {
        val right = Position(currentPosition.column + 1, currentPosition.row)
        val down = Position(currentPosition.column, currentPosition.row + 1)
        if (right == goal || down == goal) {
            workingPath.add(goal)
            return true
        }




// TODO this is wrong
        if (theBadPath.contains(right) && theBadPath.contains(down)) {
            theBadPath.add(currentPosition)
            return false
        }



    }

    class Position(val column: Int, val row: Int)

    fun Array<BooleanArray>.isRectangular(): Boolean {
        if (isEmpty()) {
            return false
        }
        val size = get(0).size
        return any { it.size != size }
    }
}