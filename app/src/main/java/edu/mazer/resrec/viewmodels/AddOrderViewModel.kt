package edu.mazer.resrec.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import edu.mazer.resrec.model.DishSearchModelState
import edu.mazer.resrec.model.MenuItem
import edu.mazer.resrec.model.MenuItemValues
import edu.mazer.resrec.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import java.text.SimpleDateFormat
import java.util.*

class AddOrderViewModel : ViewModel() {
    private val searchText: MutableStateFlow<String> = MutableStateFlow("")
    private var showProgressBar: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var matchedDish: MutableStateFlow<List<MenuItem>> = MutableStateFlow(listOf())
    private val _allDishes = MutableLiveData<MutableList<MenuItem>>()

    private val user = FirebaseAuth.getInstance().currentUser
    private val database =
        Firebase.database("https://kursovaya-5fdc1-default-rtdb.europe-west1.firebasedatabase.app/")
    private val ordersRef = database.getReference("orders")
    private val menuRef = database.getReference("menu")

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
        menuRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val menu = mutableListOf<MenuItem>()
                snapshot.children.forEach { menuItem ->
                    val key = menuItem.key
                    val menuItemValues = menuItem.getValue<MenuItemValues>()
                    menu.add(MenuItem(key!!, menuItemValues!!))
                }
                _allDishes.value = menu
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("GetMenu", error.message)
            }
        })
    }

    fun onSearchTextChanged(newText: String) {
        searchText.value = newText
        if (newText.isEmpty()) {
            matchedDish.value = arrayListOf()
            return
        }
        val dishesFromSearch = _allDishes.value!!.filter { item ->
            item.key.contains(newText, true)
        }
        matchedDish.value = dishesFromSearch
    }

    fun onClearClick() {
        searchText.value = ""
        matchedDish.value = arrayListOf()
    }

    fun onConfirmOrder(
        clientDishes: List<MenuItem>,
        clientTable: Int,
        clientNote: String?
    ) {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val time = timeFormat.format(Date())
        val status = "Готовится"

        var cost = 0
        val dishes = hashMapOf<String, Int>()
        clientDishes.forEach { item ->
            cost += item.menuItemValues.cost
            if (dishes.containsKey(item.key)) {
                val oldValue = dishes[item.key]
                dishes[item.key] = oldValue!! + 1
            }
            else
                dishes[item.key] = 1
        }

        //Key
        val simpleDate = SimpleDateFormat("yyyy_MM_dd_HH_mm")
        val date = simpleDate.format(Date())
        val key = user?.uid + date

        val order = Order(
            status = status,
            time = time,
            table = clientTable,
            cost = cost,
            dishes = dishes,
            waiter = user?.email!!,
            note = clientNote ?: "Без комментариев"
        )

        putOrderToFirestore(order, key)
    }

    private fun putOrderToFirestore(
        order: Order,
        key: String
    ) {
        ordersRef.child(key).setValue(order)
    }
}