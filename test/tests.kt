package src

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class FieldTest {
    @Test
    fun correctSize() {
        val testField = Field(5)
        assertEquals(5, testField.size)
    }

    @Test
    fun negativeSize() {
        assertThrows(IllegalArgumentException::class.java) {
            Field(-1)
        }
    }

    @Test
    fun zeroSize() {
        assertThrows(IllegalArgumentException::class.java) {
            Field(0)
        }
    }

    @Test
    fun newField() {

        val size = 5

        val newField = Field(size)

        for (row in 0 until size) {
            for (column in 0 until size) {

                assertEquals(Mark.EMPTY, newField.get(row, column))
            }
        }
    }

    @Test
    fun correctMark() {

        val size = 5
        val newField = Field(size)

        newField.set(Cell(0, 0), Mark.X)
        newField.set(Cell(0, 1), Mark.X)
        newField.set(Cell(0, 2), Mark.X)

        newField.set(Cell(1, 0), Mark.O)
        newField.set(Cell(1, 1), Mark.O)
        newField.set(Cell(1, 2), Mark.O)

        assertEquals(Mark.X, newField.get(0, 0))
        assertEquals(Mark.X, newField.get(0, 1))
        assertEquals(Mark.X, newField.get(0, 2))

        assertEquals(Mark.O, newField.get(1, 0))
        assertEquals(Mark.O, newField.get(1, 1))
        assertEquals(Mark.O, newField.get(1, 2))
    }

    @Test
    fun outOfField() {

        assertThrows(IllegalArgumentException::class.java) {
            Field(5).set(Cell(6, 0), Mark.O)
        }

        assertThrows(IllegalArgumentException::class.java) {
            Field(5).set(Cell(3, 6), Mark.X)
        }
    }

    @Test
    fun overwriteMarkIllegal() {

        val newField = Field(5)

        newField.set(Cell(1, 1), Mark.X)
        newField.set(Cell(2, 2), Mark.O)

        assertThrows(IllegalArgumentException::class.java) {
            newField.set(Cell(1, 1), Mark.O)
        }

        assertThrows(IllegalArgumentException::class.java) {
            newField.set(Cell(2, 2), Mark.X)
        }
    }

    @Test
    fun putXs() {

        val newField = Field(5)

        newField.cellAddX(Cell(1, 1))
        newField.cellAddX(Cell(4, 4))

        assertEquals(Mark.X, newField.get(1, 1))
        assertEquals(Mark.X, newField.get(4, 4))
    }

    @Test
    fun putOs() {

        val newField = Field(5)

        newField.cellAddO(Cell(1, 1))
        newField.cellAddO(Cell(4, 4))

        assertEquals(Mark.O, newField.get(1, 1))
        assertEquals(Mark.O, newField.get(4, 4))
    }

    @Test
    fun clearCell() {

        val newField = Field(30)

        newField.set(Cell(0, 0), Mark.X)
        newField.set(Cell(0, 1), Mark.X)
        newField.set(Cell(0, 2), Mark.O)
        newField.set(Cell(1, 0), Mark.X)
        newField.set(Cell(1, 1), Mark.O)
        newField.set(Cell(1, 2), Mark.O)
        newField.set(Cell(2, 0), Mark.O)
        newField.set(Cell(2, 1), Mark.O)
        newField.set(Cell(2, 2), Mark.X)

        assertEquals(Mark.O, newField.get(1, 1))

        newField.cellClear(Cell(1, 1))

        assertEquals(Mark.EMPTY, newField.get(1, 1))
    }

    @Test
    fun vertSearch() {

        val newField = Field(5)


        newField.set(Cell(0, 0), Mark.X)
        newField.set(Cell(0, 1), Mark.X)
        newField.set(Cell(0, 2), Mark.X)
        newField.set(Cell(0, 3), Mark.X)
        newField.set(Cell(0, 4), Mark.X)

        newField.set(Cell(2, 2), Mark.X)
        newField.set(Cell(2, 3), Mark.X)
        newField.set(Cell(2, 4), Mark.X)


        newField.set(Cell(3, 0), Mark.O)
        newField.set(Cell(3, 1), Mark.O)
        newField.set(Cell(3, 2), Mark.O)
        newField.set(Cell(3, 3), Mark.O)
        newField.set(Cell(3, 4), Mark.O)

        newField.set(Cell(4, 2), Mark.O)
        newField.set(Cell(4, 3), Mark.O)
        newField.set(Cell(4, 4), Mark.O)


        assertEquals(5, newField.longestX())
        assertEquals(5, newField.longestO())
    }

    @Test
    fun horSearch() {

        val newField = Field(4)

        newField.set(Cell(0, 0), Mark.X)
        newField.set(Cell(1, 0), Mark.X)
        newField.set(Cell(2, 0), Mark.X)
        newField.set(Cell(3, 0), Mark.X)

        newField.set(Cell(0, 1), Mark.O)
        newField.set(Cell(1, 1), Mark.O)

        newField.set(Cell(2, 1), Mark.X)
        newField.set(Cell(3, 1), Mark.X)

        newField.set(Cell(0, 2), Mark.O)
        newField.set(Cell(1, 2), Mark.O)
        newField.set(Cell(2, 2), Mark.O)
        newField.set(Cell(3, 2), Mark.X)


        assertEquals(4, newField.longestX())
        assertEquals(3, newField.longestO())
    }

    @Test
    fun diagSearch() {

        val newField = Field(5)

        newField.set(Cell(0, 0), Mark.X)
        newField.set(Cell(1, 1), Mark.X)
        newField.set(Cell(2, 2), Mark.X)
        newField.set(Cell(3, 3), Mark.X)
        newField.set(Cell(4, 4), Mark.X)

        newField.set(Cell(0, 4), Mark.O)
        newField.set(Cell(1, 3), Mark.O)

        newField.set(Cell(4, 2), Mark.X)

        newField.set(Cell(3, 0), Mark.O)


        assertEquals(5, newField.longestX())
        assertEquals(2, newField.longestO())
    }

    @Test
    fun anotherDiag() {

        val newField = Field(5)

        newField.set(Cell(1, 1), Mark.X)
        newField.set(Cell(2, 2), Mark.X)
        newField.set(Cell(0, 1), Mark.X)
        newField.set(Cell(0, 2), Mark.X)
        newField.set(Cell(0, 3), Mark.X)
        newField.set(Cell(1, 2), Mark.X)
        newField.set(Cell(2, 1), Mark.X)
        newField.set(Cell(3, 0), Mark.X)
        newField.set(Cell(4, 2), Mark.X)
        newField.set(Cell(2, 3), Mark.X)
        newField.set(Cell(3, 4), Mark.X)


        newField.set(Cell(3, 2), Mark.O)
        newField.set(Cell(4, 3), Mark.O)
        newField.set(Cell(1, 3), Mark.O)

        assertEquals(2, newField.longestO())
        assertEquals(4, newField.longestX())
    }

    @Test
    fun firstCheckDiag() {

        val newField = Field(5)

        newField.set(Cell(0, 3), Mark.X)
        newField.set(Cell(1, 2), Mark.X)
        newField.set(Cell(2, 1), Mark.X)
        newField.set(Cell(3, 0), Mark.X)

        assertEquals(4, newField.longestX())
    }

    @Test
    fun firstCheckDiagThree() {

        val newField = Field(5)

        newField.set(Cell(0, 2), Mark.X)
        newField.set(Cell(1, 1), Mark.X)
        newField.set(Cell(2, 0), Mark.X)

        assertEquals(3, newField.longestX())
    }

    @Test
    fun firstCheckDiagDouble() {

        val newField = Field(5)

        newField.set(Cell(0, 1), Mark.X)
        newField.set(Cell(1, 0), Mark.X)

        assertEquals(2, newField.longestX())
    }

    @Test
    fun secondCheckDiag() {

        val newField = Field(5)

        newField.set(Cell(1, 0), Mark.X)
        newField.set(Cell(2, 1), Mark.X)
        newField.set(Cell(3, 2), Mark.X)
        newField.set(Cell(4, 3), Mark.X)

        assertEquals(4, newField.longestX())
    }

    @Test
    fun thirdCheckDiag() {

        val newField = Field(5)

        newField.set(Cell(4, 1), Mark.X)
        newField.set(Cell(3, 2), Mark.X)
        newField.set(Cell(2, 3), Mark.X)
        newField.set(Cell(1, 4), Mark.X)

        assertEquals(4, newField.longestX())
    }

    @Test
    fun fourthCheckDiag() {

        val newField = Field(5)

        newField.set(Cell(0, 1), Mark.X)
        newField.set(Cell(1, 2), Mark.X)
        newField.set(Cell(2, 3), Mark.X)
        newField.set(Cell(3, 4), Mark.X)

        assertEquals(4, newField.longestX())
    }
}
