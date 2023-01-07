package edu.mazer.resrec.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import edu.mazer.resrec.model.DishInOrder
import edu.mazer.resrec.model.Order

class HomeViewModel: ViewModel() {
    private val _currentOrders: MutableLiveData<MutableList<Order>> = MutableLiveData()
    val currentOrders: LiveData<MutableList<Order>> = _currentOrders

    val myAuth = FirebaseAuth.getInstance()

    val testOrder = Order(
        id = "0001",
        status = "Готовится",
        time = "16:33",
        table = 4,
        cost = 785,
        dishes = mutableListOf(
            DishInOrder(
                key = "Картофель фри",
                cost = 124
            ),
            DishInOrder(
                key = "Кола бол.",
                cost = 523
            ),
            DishInOrder(
                key = "Гамбургер",
                cost = 53
            )
        ),
        waiter = "Иванов И.И.",
        note = "стандартно"
    )

    init {
        _currentOrders.value = mutableListOf(testOrder)
    }

    fun signOut(){
        myAuth.signOut()
    }

}