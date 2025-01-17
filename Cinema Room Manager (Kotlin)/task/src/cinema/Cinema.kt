package cinema

const val TITLE = "Cinema:"
const val NUM_OF_ROW = 7
const val NUM_OF_COL = 8
const val SEAT = "S"
const val TOP_HEADER = "  1 2 3 4 5 6 7 8"

const val SMALL_CAPACITY = 60
const val REGULAR_PRICE = 10
const val BACK_PRICE = 8
const val ROW_PROMPT = "Enter the number of rows:"
const val COL_PROMPT = "Enter the number of seats in each row:"
const val INCOME_PROMPT = "Total income:"

fun main() {
    calculateIncome()
}

fun calculateIncome() {
    println(ROW_PROMPT)
    val numOfRow = readln().toInt()

    println(COL_PROMPT)
    val numOfCol = readln().toInt()

    val totalSeats = numOfRow * numOfCol

    val totalIncome = if (totalSeats <= SMALL_CAPACITY) {
        totalSeats * REGULAR_PRICE
    } else {
        val frontRow = numOfRow / 2
        val backRow = if (numOfRow % 2 == 1) (numOfRow / 2) + 1 else numOfRow / 2

        val frontSeatPrices = frontRow * numOfCol * REGULAR_PRICE
        val backSeatPricess = backRow * numOfCol * BACK_PRICE

        frontSeatPrices + backSeatPricess
    }

    println(INCOME_PROMPT)
    println("$$totalIncome")
}

fun displayStatus() {
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