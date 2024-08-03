package com.example.expensetracker.presentation.HomeScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.ExpenseDao
import com.example.expensetracker.data.ExpenseEntity
import com.example.expensetracker.utilities.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(private val dao: ExpenseDao) : ViewModel() {

    val expense = dao.getExpenses()


    fun deleteDatabase():Boolean {
        viewModelScope.launch(Dispatchers.IO) {
           dao.deleteData()
        }
        return true
    }

    fun getBalance(list: List<ExpenseEntity>): String {

        var total = 0.0

        list.forEach {
            if (it.accountType == "Income") {
                total += it.amount
            } else {
                total -= it.amount
            }
        }

        return "$ ${Util.formatToDecimalValue(total)}"
    }


    fun getTotalExpense(list: List<ExpenseEntity>): String {
        var total = 0.0

        list.forEach {
            if (it.accountType == "Expense") {
                total += it.amount
            }
        }
        return "$ ${Util.formatToDecimalValue(total)}"

    }

    fun getTotalIncome(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach {
            if (it.accountType == "Income") {
                total += it.amount
            }
        }

        return "$ ${Util.formatToDecimalValue(total)}"
    }
}