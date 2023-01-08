package edu.mazer.resrec.model

data class MenuItem(
    val key: String = "",
    val menuItemValues: MenuItemValues = MenuItemValues()
)

data class MenuItemValues(
    val cost: Int = -1,
    val ingredient: String = ""
)
