package edu.mazer.resrec.viewmodels

import androidx.lifecycle.ViewModel
import edu.mazer.resrec.model.DishSearchModelState
import edu.mazer.resrec.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class DishSearchViewModel: ViewModel() {
    private var allUsers: ArrayList<MenuItem> = ArrayList()
    private val searchText: MutableStateFlow < String > = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedUsers: MutableStateFlow<List<MenuItem>> = MutableStateFlow(arrayListOf())

    val dishSearchModelState = combine(
        searchText,
        matchedUsers,
        showProgressBar
    ) {
            text, matchedUsers, showProgress ->

        DishSearchModelState(
            text,
            matchedUsers,
            showProgress
        )
    }

    init {
        retrieveUsers()
    }

    fun retrieveUsers() {
        val users = arrayListOf<MenuItem>(
            MenuItem(
                key = "afwefae",
                cost = 150,
                ingredients = "a f w e f a e"
            ),
            MenuItem(
                key = "herh",
                cost = 250,
                ingredients = "aehrhaefhsdgaseegsshsdertjhesdjhdsfhgkjaehblkgfhveasirouhgipouerghipuh"
            ),
            MenuItem(
                key = "tlyuulktedy",
                cost = 50,
                ingredients = "dtykkkkkkkmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmszassddddddddddddddddwgw"
            )
        )

        if (users != null) {
            allUsers.addAll(users)
        }
    }

    fun onSearchTextChanged(changedSearchText: String) {
        searchText.value = changedSearchText
        if (changedSearchText.isEmpty()) {
            matchedUsers.value = arrayListOf()
            return
        }
        val usersFromSearch = allUsers.filter { item ->
            item.key.contains(changedSearchText, true)
        }
        matchedUsers.value = usersFromSearch
    }

    fun onClearClick() {
        searchText.value = ""
        matchedUsers.value = arrayListOf()
    }
}