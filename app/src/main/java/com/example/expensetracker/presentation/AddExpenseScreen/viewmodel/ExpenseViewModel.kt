package com.example.expensetracker.presentation.AddExpenseScreen.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.ExpenseDao
import com.example.expensetracker.data.ExpenseDatabase
import com.example.expensetracker.data.ExpenseEntity
import com.example.expensetracker.utilities.Category
import kotlinx.coroutines.flow.MutableStateFlow

class ExpenseViewModel(private val dao: ExpenseDao) : ViewModel() {

    var category = MutableStateFlow(Category.PAY_PAL)
        private set

    fun selectCategory(category: Category) {
        this.category.value = category
    }

    suspend fun addExpense(expenseEntity: ExpenseEntity): Boolean {

        try {
            Log.e("viewmodel-->","addExpense")
            dao.insertExpense(expenseEntity)

        } catch (e: Exception) {
            return false
        }
        return true
    }
}


class AddExpenseViewModelFactory(private val context: Context):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)){
            val dao=ExpenseDatabase.getDatabase(context).expenseDao()
            return ExpenseViewModel(dao) as T
        }
        else{
            throw IllegalArgumentException("Unknown View Model Class")
        }
    }
}
