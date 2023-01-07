package edu.mazer.resrec.viewmodels

import androidx.lifecycle.ViewModel
import edu.mazer.resrec.model.DishSearchModelState
import edu.mazer.resrec.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class DishSearchViewModel : ViewModel() {
    private var allDishes: ArrayList<MenuItem> = ArrayList()
    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedDish: MutableStateFlow<List<MenuItem>> = MutableStateFlow(listOf())


    val dishSearchModelState = combine(
        searchText,
        matchedDish,
        showProgressBar
    ) { text, matchedUsers, showProgress ->
        DishSearchModelState(text, matchedUsers, showProgress)
    }

    init {
        getMenu()
    }

    private fun getMenu() {
        val menu = arrayListOf(
            MenuItem(
                key = "Картофель фри",
                cost = 150,
                ingredients = "Картофель, соль, растительное масло"
            ),
            MenuItem(
                key = "Гамбургер",
                cost = 350,
                ingredients = "Говядина, Булочки пшен., Огурцы мар., Кетчуп, Сыр, Лук фри"
            ),
            MenuItem(
                key = "Кола бол.",
                cost = 75,
                ingredients = "Кока-кола"
            )
        )

        if (menu != null) {
            allDishes.addAll(menu)
        }
    }

    fun onSearchTextChanged(newText: String) {
        searchText.value = newText
        if (newText.isEmpty()) {
            matchedDish.value = arrayListOf()
            return
        }
        val usersFromSearch = allDishes.filter { item ->
            item.key.contains(newText, true)
        }
        matchedDish.value = usersFromSearch
    }

    fun onClearClick() {
        searchText.value = ""
        matchedDish.value = arrayListOf()
    }
}