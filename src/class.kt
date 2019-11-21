package src

import java.lang.IllegalArgumentException
import kotlin.math.max

enum class Mark {
    X, O, EMPTY
}

data class Cell(val row: Int, val column: Int)

class Field(val size: Int) {
    private var field = mutableMapOf<Cell, Mark>()

    init {
        if (size <= 0) throw IllegalArgumentException()
        for (row in 0 until size) {
            for (column in 0 until size)
                field[Cell(row, column)] = Mark.EMPTY
        }
    }

    fun get(row: Int, column: Int): Mark = get(Cell(row, column))
    fun get(cell: Cell): Mark = field[cell] ?: throw IllegalArgumentException()

    fun set(row: Int, column: Int, value: Mark) = set(Cell(row, column), value)
    fun set(cell: Cell, value: Mark) {
        if (field[cell] == Mark.EMPTY) {
            field[cell] = value
        } else throw IllegalArgumentException()
    }

    fun cellAddX(cell: Cell) = this.set(cell, Mark.X)

    fun cellAddO(cell: Cell) = this.set(cell, Mark.O)

    fun cellClear(cell: Cell) {
        if (get(cell) == Mark.X || get(cell) == Mark.O)
            field[cell] = Mark.EMPTY
    }

    fun longestX() = findLongest(Mark.X)

    fun longestO() = findLongest(Mark.O)

    private fun findLongest(sign: Mark): Int {
        var sequence = 0
        var longestSequence = 0
        var seqFirstDiagonalQuarter = 0
        var seqSecondDiagonalQuarter = 0
        var seqThirdDiagonalQuarter = 0
        var seqFourthDiagonalQuarter = 0

        for (row in 0 until size) {
            for (column in 0 until size) {
                if (get(Cell(row, column)) == sign) {
                    sequence++
                } else {
                    longestSequence = max(sequence, longestSequence)
                    sequence = 0
                }
            }
            longestSequence = max(sequence, longestSequence)
            sequence = 0
        }

        for (column in 0 until size) {
            for (row in 0 until size) {
                if (get(Cell(row, column)) == sign) {
                    sequence++
                } else {
                    longestSequence = max(sequence, longestSequence)
                    sequence = 0
                }
            }
            longestSequence = max(sequence, longestSequence)
            sequence = 0
        }

        for (rowStartIndex in 0 until size) {
            for ((columnIndexDiagonalRight, rowIndex) in (rowStartIndex until size).withIndex()) {
                if (get(rowIndex, columnIndexDiagonalRight) == sign) {
                    seqFirstDiagonalQuarter++
                } else {
                    longestSequence = max(seqFirstDiagonalQuarter, longestSequence)
                    seqFirstDiagonalQuarter = 0
                }
                if (get(columnIndexDiagonalRight, rowIndex) == sign) {
                    seqSecondDiagonalQuarter++
                } else {
                    longestSequence = max(seqSecondDiagonalQuarter, longestSequence)
                    seqSecondDiagonalQuarter = 0
                }

                val columnIndexDiagonalLeft = size - 1 - columnIndexDiagonalRight
                if (get(rowIndex, columnIndexDiagonalLeft) == sign) {
                    seqThirdDiagonalQuarter++
                } else {
                    longestSequence = max(seqThirdDiagonalQuarter, longestSequence)
                    seqThirdDiagonalQuarter = 0
                }
                if (get(columnIndexDiagonalRight, size - 1 - rowIndex) == sign) {
                    seqFourthDiagonalQuarter++
                } else {
                    longestSequence = max(seqFourthDiagonalQuarter, longestSequence)
                    seqFourthDiagonalQuarter = 0
                }
            }
            longestSequence = max(seqFirstDiagonalQuarter, longestSequence)
            seqFirstDiagonalQuarter = 0
            longestSequence = max(seqSecondDiagonalQuarter, longestSequence)
            seqSecondDiagonalQuarter = 0
            longestSequence = max(seqThirdDiagonalQuarter, longestSequence)
            seqThirdDiagonalQuarter = 0
            longestSequence = max(seqFourthDiagonalQuarter, longestSequence)
            seqFourthDiagonalQuarter = 0
        }
        return longestSequence
    }
}