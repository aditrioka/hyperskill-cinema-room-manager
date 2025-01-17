package cinema

fun main() {
    val cinema = Cinema()
    cinema.operate()
}

class Cinema {

    private var numOfCol = 0
    private var numOfRow = 0
    private var purchasedTickets = 0
    private var income = 0
    private var totalIncome = 0

    private val totalSeats
        get() = numOfRow * numOfCol

    private val numOfFrontRow
        get() = numOfRow / 2

    private val numOfBackRow
        get() = if (numOfRow % 2 == 1) (numOfRow / 2) + 1 else numOfRow / 2

    private val isSmallCapacity
        get() = totalSeats <= SMALL_CAPACITY

    private val purchasedTicketsPercent: String
        get() = PERCENT_FORMAT.format((purchasedTickets.toDouble() / totalSeats) * 100)

    private val seats = mutableListOf<MutableList<Seat>>()

    fun operate() {
        promptRowAndCol()

        do {
            promptOperation()
            val operation = readln().toInt()

            when(operation) {
                Operation.SHOW_STATE.number -> showState()
                Operation.BUY_TICKET.number -> buyTicket()
                Operation.STATISTICS.number -> showStatistics()
                Operation.EXIT.number -> continue
            }
        } while(operation != Operation.EXIT.number)

    }

    private fun promptRowAndCol() {
        println(INIT_ROW_PROMPT)
        numOfRow = readln().toInt()

        println(INIT_COL_PROMPT)
        numOfCol = readln().toInt()

        initSeatsState()
    }

    private fun promptOperation() {
        val promptOperation = """
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
        """.trimIndent()
        println(promptOperation)
    }

    private fun initSeatsState() {
        for (row in 0..<numOfRow) {
            seats.add(mutableListOf())
            for (col in 0..<numOfCol) {
                val seatPrice = initSeatPrice(row)
                seats[row].add(Seat(Status.SEAT.text, seatPrice))
                totalIncome += seatPrice
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

    private fun buyTicket() {
        do {
            var canBuyTicket = false
            try {
                println(GET_PRICE_ROW_PROMPT)
                val row = readln().toInt().dec()

                println(GET_PRICE_COL_PROMPT)
                val col = readln().toInt().dec()

                val seat = seats[row][col]
                canBuyTicket = isTicketAvailable(seat)

                if (canBuyTicket) {
                    val seatPrice = seat.price
                    seats[row][col] = seat.copy(status = Status.BOOKED.text)

                    purchasedTickets++
                    income += seatPrice

                    println("$GET_SEAT_PRICE_PROMPT $$seatPrice")
                } else {
                    println(BUY_FAILED_PROMPT)
                }
            } catch (e: IndexOutOfBoundsException) {
                println(WRONG_INPUT_PROMPT)
            }
        } while(!canBuyTicket)
    }

    private fun isTicketAvailable(seat: Seat): Boolean {
        return seat.status != Status.BOOKED.text
    }

    private fun showState() {
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

    private fun showStatistics() {
        println("$PURCHASED_TICKET $purchasedTickets")
        println("$PERCENTAGE $purchasedTicketsPercent%")
        println("$CURRENT_INCOME $$income")
        println("$TOTAL_INCOME $$totalIncome")
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

        const val PURCHASED_TICKET = "Number of purchased tickets: "
        const val PERCENTAGE = "Percentage: "
        const val CURRENT_INCOME = "Current income: "
        const val TOTAL_INCOME = "Total income: "
        const val PERCENT_FORMAT = "%.2f"
        const val BUY_FAILED_PROMPT = "That ticket has already been purchased!"
        const val WRONG_INPUT_PROMPT = "Wrong input!"
    }

    data class Seat(var status: String, val price: Int)

    enum class Status(val text: String) {
        SEAT("S"),
        BOOKED("B")
    }

    enum class Operation(val number: Int) {
        SHOW_STATE(1),
        BUY_TICKET(2),
        STATISTICS(3),
        EXIT(0)
    }
}