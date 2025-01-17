package cinema

fun main() {
    val cinema = Cinema()
    cinema.promptRowAndCol()
    cinema.displayStatus()
    cinema.getSeatPrice()
    cinema.displayStatus()
}

class Cinema {

    private var numOfCol = 0
    private var numOfRow = 0

    private val totalSeats
        get() = numOfRow * numOfCol

    private val numOfFrontRow
        get() = numOfRow / 2

    private val numOfBackRow
        get() = if (numOfRow % 2 == 1) (numOfRow / 2) + 1 else numOfRow / 2

    private val isSmallCapacity
        get() = totalSeats <= SMALL_CAPACITY

    private val seats = mutableListOf<MutableList<Seat>>()

    fun promptRowAndCol() {
        println(INIT_ROW_PROMPT)
        numOfRow = readln().toInt()

        println(INIT_COL_PROMPT)
        numOfCol = readln().toInt()

        initSeatsState()
    }

    private fun initSeatsState() {
        for (row in 0..<numOfRow) {
            seats.add(mutableListOf())
            for (col in 0..<numOfCol) {
                val seatPrice = initSeatPrice(row)
                seats[row].add(Seat(Status.SEAT.text, seatPrice))
            }
        }
    }

    private fun initSeatPrice(row: Int): Int {
        return if (isSmallCapacity) {
            REGULAR_PRICE
        } else {
            if (row.inc() > numOfFrontRow) BACK_PRICE else REGULAR_PRICE
        }
    }

    fun calculateIncome() {
        val totalIncome = if (isSmallCapacity) {
            totalSeats * REGULAR_PRICE
        } else {
            val frontSeatPrices = numOfFrontRow * numOfCol * REGULAR_PRICE
            val backSeatPrices = numOfBackRow * numOfCol * BACK_PRICE

            frontSeatPrices + backSeatPrices
        }

        println(INCOME_PROMPT)
        println("$$totalIncome")
    }

    fun getSeatPrice() {
        println(GET_PRICE_ROW_PROMPT)
        val row = readln().toInt().dec()
        println(GET_PRICE_COL_PROMPT)
        val col = readln().toInt().dec()

        val seat = seats[row][col]
        val seatPrice = seat.price
        seats[row][col] = seat.copy(status = Status.BOOKED.text)

        println("$GET_SEAT_PRICE_PROMPT $$seatPrice")
    }

    fun displayStatus() {
        println(TITLE)
        printHeader()

        for (row in 0..<numOfRow) {
            print("${row.inc()} ")
            for (col in 0..<numOfCol) {
                print(seats[row][col].status)
                if (col < numOfCol.dec()) print(" ")
            }
            println()
        }
        println()
    }

    private fun printHeader() {
        print("  ")
        repeat(numOfCol) { col ->
            print(col.inc())
            if (col <= numOfCol) print(" ")
        }
        println()
    }

    companion object {
        const val TITLE = "Cinema: "
        const val SMALL_CAPACITY = 60
        const val REGULAR_PRICE = 10
        const val BACK_PRICE = 8
        const val INIT_ROW_PROMPT = "Enter the number of rows:"
        const val INIT_COL_PROMPT = "Enter the number of seats in each row:"
        const val GET_PRICE_ROW_PROMPT = "Enter a row number:"
        const val GET_PRICE_COL_PROMPT = "Enter a seat number in that row:"
        const val GET_SEAT_PRICE_PROMPT = "Ticket price: "
        const val INCOME_PROMPT = "Total income:"
    }

    data class Seat(var status: String, val price: Int)

    enum class Status(val text: String) {
        SEAT("S"),
        BOOKED("B")
    }
}