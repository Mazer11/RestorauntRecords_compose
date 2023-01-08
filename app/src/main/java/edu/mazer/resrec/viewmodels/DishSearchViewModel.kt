package edu.mazer.resrec.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import edu.mazer.resrec.model.DishSearchModelState
import edu.mazer.resrec.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class DishSearchViewModel : ViewModel() {
    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedDish: MutableStateFlow<List<MenuItem>> = MutableStateFlow(listOf())

    private val _allDishes = MutableLiveData<MutableList<MenuItem>>()
    val allDishes: LiveData<MutableList<MenuItem>> = _allDishes

    private val database = Firebase.database
    val oredersRef = database.getReference("orders")
    val menuRef = database.getReference("menu")

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

    }

    fun onSearchTextChanged(newText: String) {
        searchText.value = newText
        if (newText.isEmpty()) {
            matchedDish.value = arrayListOf()
            return
        }
        val usersFromSearch = _allDishes.value!!.filter { item ->
            item.key.contains(newText, true)
        }
        matchedDish.value = usersFromSearch
    }

    fun onClearClick() {
        searchText.value = ""
        matchedDish.value = arrayListOf()
    }
}