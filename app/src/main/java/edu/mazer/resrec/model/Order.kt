package edu.mazer.resrec.model

data class Order(
    val id: String,
    val status: String,
    val time: String,
    val table: Int,
    val cost: Int,
    val dishes: MutableList<DishInOrder>,
    val waiter: String,
    val note: String
)
