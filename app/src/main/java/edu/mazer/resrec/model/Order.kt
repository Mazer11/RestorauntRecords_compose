package edu.mazer.resrec.model

data class Order(
    val status: String = "",
    val time: String = "",
    val table: Int = -1,
    val cost: Int = -1,
    val dishes: HashMap<String, Int> = HashMap(),
    val waiter: String = "",
    val note: String = ""
)
