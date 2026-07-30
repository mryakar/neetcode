package me.yakar.neetcode

import spock.lang.Specification
import spock.lang.Subject

class ValidSudokuSpecification extends Specification {

    private static final char[][] EMPTY = board(
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            "........."
    )

    private static final char[][] VALID_FILLED = board(
            "12..3....",
            "4..5.....",
            ".98.....3",
            "5...6...4",
            "...8.3..5",
            "7...2...6",
            "......2..",
            "...419..8",
            "....8..79"
    )

    private static final char[][] DUPLICATE_IN_ROW = board(
            ".5..5....",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            "........."
    )

    private static final char[][] DUPLICATE_IN_COLUMN = board(
            "5........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            "5........",
            ".........",
            "........."
    )

    private static final char[][] DUPLICATE_IN_BOX = board(
            ".5.......",
            "..5......",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            "........."
    )

    @Subject
    def solution = new ValidSudoku()

    def "should accept #scenario"() {
        expect:
        solution.isValidSudoku(candidate)

        where:
        scenario                                                       | candidate
        "a board with no filled cells, since a board need not be full" | EMPTY
        "a partially filled board that breaks no rule"                 | VALID_FILLED
    }

    def "should reject a board with #scenario"() {
        expect:
        !solution.isValidSudoku(candidate)

        where:
        scenario                                | candidate
        "a duplicate digit in the same row"     | DUPLICATE_IN_ROW
        "a duplicate digit in the same column"  | DUPLICATE_IN_COLUMN
        "a duplicate digit in the same 3x3 box" | DUPLICATE_IN_BOX
    }

    def "should give the same verdict for a board and its transpose"() {
        expect:
        solution.isValidSudoku(candidate) == solution.isValidSudoku(transpose(candidate))

        where:
        candidate << [EMPTY, VALID_FILLED, DUPLICATE_IN_ROW, DUPLICATE_IN_COLUMN, DUPLICATE_IN_BOX]
    }

    private static char[][] board(String... rows) {
        rows.collect { it as char[] } as char[][]
    }

    private static char[][] transpose(char[][] source) {
        char[][] result = new char[9][9]
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                result[c][r] = source[r][c]
            }
        }
        result
    }
}
