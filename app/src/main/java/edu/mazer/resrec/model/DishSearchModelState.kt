package edu.mazer.resrec.model

data class DishSearchModelState(
    val searchText: String = "",
    val dishes: List<MenuItem> = arrayListOf(),
    val showProgressBar: Boolean = false
) {
    companion object {
        val Empty = DishSearchModelState()
    }
}
