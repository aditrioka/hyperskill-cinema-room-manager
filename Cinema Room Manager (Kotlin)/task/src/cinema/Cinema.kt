package cinema

const val TITLE = "Cinema:"
const val NUM_OF_ROW = 7
const val NUM_OF_COL = 8
const val SEAT = "S"
const val TOP_HEADER = "  1 2 3 4 5 6 7 8"

fun main() {
    println(TITLE)
    println(TOP_HEADER)
    for (row in 0..<NUM_OF_ROW) {
        print("${row.inc()} ")
        for (col in 0..<NUM_OF_COL) {
            print(SEAT)
            if (col < NUM_OF_COL.dec()) print(" ")
        }
        println()
    }
}